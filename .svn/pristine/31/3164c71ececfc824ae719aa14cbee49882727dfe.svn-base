package com.sumridge.smart.service;

import com.sumridge.smart.bean.ResultBean;
import com.sumridge.smart.bean.SaleAggBean;
import com.sumridge.smart.bean.SaleDisplayBean;
import com.sumridge.smart.bean.SaleInxBean;
import com.sumridge.smart.entity.*;
import com.sumridge.smart.dao.SaleInfoRepository;
import com.sumridge.smart.msdcache.MsdCache;
import com.sumridge.smart.query.ContactQuery;
import com.sumridge.smart.query.FileTraceQuery;
import com.sumridge.smart.query.SaleQuery;
import com.sumridge.smart.query.UserQuery;
import com.sumridge.smart.util.FileUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liu on 16/5/12.
 */
@Service
public class SaleService {
    @Autowired
    private SaleQuery saleQuery;
    @Autowired
    private UserQuery userQuery;
    @Autowired
    private ContactService contactService;
    @Autowired
    private SaleInfoRepository saleInfoRepository;
    @Autowired
    private IndexService indexService;
    @Autowired
    private FileTraceQuery fileTraceQuery;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private AccountService accountService;

    @Autowired
    private MsdCache msdCache;

    private static final Logger LOGGER = LoggerFactory.getLogger(SaleService.class);

    public ResultBean getSaleList(UserInfo userInfo) {

        //TODO page logic
        return ResultBean.getSuccessResult(saleQuery.getSaleList(20));
    }

    /**
     * create sale info
     *
     * @param bean
     * @param userInfo
     * @return
     */
    public ResultBean saveInfo(SaleBean bean, UserInfo userInfo) {

        SaleInfo saleInfo = new SaleInfo();
        BeanUtils.copyProperties(bean, saleInfo);

        //set mapping info
        LOGGER.info("start contact get");
        Contact contact = contactService.getContactByMapping(bean.getOppTrader(), bean.getBrokerInitials(), bean.getEcnSource());
        if(contact != null) {
            saleInfo.setContactId(contact.getId());
            saleInfo.setContactName(contact.getFirstName() + " " + contact.getLastName());
        }
        LOGGER.info("end contact get");
        LOGGER.info("start user get");
        UserInfo saleUser = userQuery.getIdBySale(bean.getSalesId());
        if(saleUser != null) {
            saleInfo.setTraderId(saleUser.getId());
            saleInfo.setTraderName(saleUser.getFirstName() + " " + saleUser.getLastName());
        }
        LOGGER.info("end user get");
        LOGGER.info("start company get");
        CompanyInfo companyInfo = accountService.getAccountByMapping(bean.getBrokerInitials(), bean.getEcnSource());
        if(companyInfo != null) {
            saleInfo.setCompanyId(companyInfo.getId());
            saleInfo.setCompanyName(companyInfo.getLongName());
            saleInfo.setAccountId(companyInfo.getAccountInfos().get(0).getId());
            saleInfo.setAccountName(companyInfo.getAccountInfos().get(0).getLongName());
        }
        LOGGER.info("end company get");

        LOGGER.info("start save sale info");
        //set base info
        saleInfo.setCreateDate(new Date());
        saleInfo.setCreateUser(userInfo.getId());
        saleInfo.setCreateName(userInfo.getFirstName()+ " " + userInfo.getLastName());
        saleInfoRepository.save(saleInfo);
        LOGGER.info("end save sale info");

        LOGGER.info("start index sale info");
        //extend base info
        SaleInxBean inxBean = new SaleInxBean();
        BeanUtils.copyProperties(saleInfo, inxBean);
        inxBean.setMsd(msdCache.getMsdData(saleInfo.getCusip()));
        if(inxBean.getMsd() != null) {
            indexService.createIndex(inxBean, saleInfo.getId());
        }
        LOGGER.info("end index sale info");

        return ResultBean.getSuccessResult();
    }

    public ResultBean getRecentList(UserInfo userInfo) {
        List<SaleInfo> infoList = saleQuery.getSaleList(5);

        List<SaleDisplayBean> resulteList = new ArrayList<>();

        infoList.forEach(saleInfo -> {
            SaleDisplayBean bean = new SaleDisplayBean();
            BeanUtils.copyProperties(saleInfo, bean);
            String contactName = contactService.getContactName(bean.getContactId());
            bean.setContactName(contactName);
            resulteList.add(bean);
        });
        return ResultBean.getSuccessResult(resulteList);
    }

    public ResultBean getSalePercent(UserInfo userInfo) {
        List<SaleAggBean> saleAggBeanList = saleQuery.getPercent(userInfo.getId());
        setContactName(saleAggBeanList);
        return ResultBean.getSuccessResult(saleAggBeanList);
    }

    private void setContactName(List<SaleAggBean> saleAggBeanList) {

        if(saleAggBeanList != null && saleAggBeanList.size() > 0) {
            saleAggBeanList.forEach(saleAggBean -> {
                String contactName = contactService.getContactName(saleAggBean.getId());
                saleAggBean.setContactName(contactName);
            });

        }
    }


    public ResultBean saveFile(UserInfo userInfo, MultipartFile file) {

        if(!checkValidUploadFile(file)) {
            //check file value
            try {

                Reader reader = new InputStreamReader(file.getInputStream());
                CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL.withHeader().withDelimiter(','));
                try {
                    for(final CSVRecord record : parser) {
                        LOGGER.info(record.toString());
//                        LOGGER.info("redis cache:"+cacheService.getValue("test"));
                        //insert into temp sale
                        SaleBean bean = new SaleBean();
                        bean.setBrokerInitials(record.get("brokerInitials").trim());
                        bean.setBuySell(record.get("buySell").trim());
                        bean.setCusip(record.get("cusip").trim());
                        bean.setEcnSource(record.get("ECNSOURCE").trim());
                        bean.setNetPrice(NumberUtils.toDouble(record.get("netprice")));
                        bean.setNetYield(NumberUtils.toDouble(record.get("netyield")));
                        bean.setOppTrader(record.get("oppTrader").trim());
                        bean.setQuantity(NumberUtils.toDouble(record.get("quantity")));
                        bean.setSalesId(record.get("CrmUserId"));
                        bean.setSecId(record.get("secId").trim());
                        bean.setSettleDate(DateUtils.parseDate(record.get("settledate"), "yyyy/MM/dd"));
                        bean.setTradeDate(DateUtils.parseDate(record.get("tradedate"), "yyyy/MM/dd"));
                        bean.setTraderInitials(record.get("traderInitials").trim());
                        saveInfo(bean, userInfo);

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                } finally {
                    parser.close();
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ResultBean.getSuccessResult();

        } else {

            return ResultBean.geFailResult(null, "file upload failed");
        }

    }

    private boolean checkValidUploadFile(MultipartFile file) {

        try {
            String md5Str = FileUtil.toMD5String(file.getInputStream());
            return fileTraceQuery.isExsit(md5Str, "sale-info", "s");

        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
    }

    //add by zj
    public ResultBean loadPagedSales(int currentPage, int pageSize){
        Map<String, Object> map = new HashMap();
        List<SaleInfo> list = saleQuery.getPagedSales(currentPage,pageSize);
        long maxSize = saleQuery.countSales();
        long maxPage = maxSize/pageSize;
        map.put("salesPage",list);
        map.put("maxSize",maxSize);
        map.put("maxPage",maxPage);
        return ResultBean.getSuccessResult(map);
    }
}
