package com.sumridge.smart.service;

import com.sumridge.smart.bean.AccountAggBean;
import com.sumridge.smart.bean.AccountDetailBean;
import com.sumridge.smart.bean.AccountTradeBean;
import com.sumridge.smart.bean.ResultBean;
import com.sumridge.smart.entity.*;
import com.sumridge.smart.query.AccountQuery;
import com.sumridge.smart.query.SaleQuery;
import com.sumridge.smart.util.CodeUtil;
import com.sumridge.smart.util.DateUtil;
import com.sumridge.smart.util.StringUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import java.util.List;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by liu on 16/4/25.
 */
@Service
public class AccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountQuery accountQuery;
    @Autowired
    private BoardService boardService;
    @Autowired
    private SaleQuery saleQuery;
    public ResultBean getAccountList(UserInfo userInfo) {

        List<AccountAggBean> accountList =accountQuery.queryList();

        return ResultBean.getSuccessResult(accountList);
    }

    public ResultBean saveAccount(AccountDetailBean bean, String source) {
        bean.setInitial(StringUtil.getInitChar(bean.getName()));
        if(accountQuery.saveAccount(bean, source)){
            boardService.createAccountBoard(bean);
        }

        return ResultBean.getSuccessResult();
    }

    public ResultBean getCompanyInfo(String accountId) {

        return ResultBean.getSuccessResult(accountQuery.queryAccount(accountId));
    }

    /**
     * upload counterparty into system
     *
     * @param userInfo
     * @param file
     * @return
     */
    public ResultBean saveFile(UserInfo userInfo, MultipartFile file) {
        //TODO change to async
        //TODO logic validation
        try {
            Reader reader = new InputStreamReader(file.getInputStream());
            CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL.withHeader().withDelimiter(','));
            try {
                for(final CSVRecord record : parser) {
//
                    LOGGER.info(record.toString());

                    AccountInfo info = new AccountInfo();

                    info.setCreateDate(new Date());

                    info.setCreator(userInfo.getId());
                    info.setName(record.get("ShortName"));
                    info.setInitial(StringUtil.getInitChar(info.getName()));
                    info.setLongName(record.get("CounterpartyName"));
                    info.setSalesCoverageId(record.get("salesCoverageId"));
                    info.setCategoryId(record.get("categoryId"));
                    info.setCounterpartyGroupId(record.get("counterpartyGroupId"));
                    info.setReviewInd(record.get("reviewInd"));
                    info.setStatusInd(record.get("statusInd"));
                    info.setCreateSource(CodeUtil.CreateSourceType.FROM_FILE);
                    accountQuery.insertAccount(info, record.get("Company"));

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                parser.close();
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResultBean.getSuccessResult();
    }

    public ResultBean saveMapFile(UserInfo userInfo, MultipartFile file) {

        try {

            Reader reader = new InputStreamReader(file.getInputStream());
            CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL.withHeader().withDelimiter(','));
            try {
                for(final CSVRecord record : parser) {
//
                    LOGGER.info(record.toString());
                    String companyName = record.get("companyName");
                    String counterPartyName = record.get("counterPartyName");

                    if(StringUtils.isNotEmpty(companyName) && StringUtils.isNotEmpty(counterPartyName)) {
                        SaleMapping info = new SaleMapping();
                        info.setId(record.get("brokerInitials"));
                        info.setSource(record.get("platform"));
                        accountQuery.addAccountMap(info, counterPartyName, companyName);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                parser.close();
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResultBean.getSuccessResult();
    }

    public CompanyInfo getAccountByMapping(String brokerInitials, String ecnSource) {

        return accountQuery.getAccountByMapping(brokerInitials, ecnSource);
    }

    public ResultBean getAccountTradeList(UserInfo userInfo, String fromDate, String toDate) {
        Date from = DateUtil.getDate(fromDate);
        Date to = DateUtil.getDate(toDate);
        //get account
        List<AccountTradeBean> list = accountQuery.querySimpleList(from, to);
        list.forEach(accountTradeBean -> {
            SaleInfo sale = saleQuery.getLatestSale(accountTradeBean.getId());
            accountTradeBean.setSaleInfo(sale);
        });

        return ResultBean.getSuccessResult(list);
    }

    //add by zj 16/12/13
    public void addPortfolio(PortfolioInfo portfolioInfo){
        accountQuery.addPortfolio(portfolioInfo);
    }

    //add by zj 16/12/14
    public void uploadPortfolio(String id, MultipartFile file, String title){
        try{
            Reader reader = new InputStreamReader(file.getInputStream());
            CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL.withHeader().withDelimiter(','));
            PortfolioInfo portfolioInfo = new PortfolioInfo();
            List<Map> list = new ArrayList<Map>();
            for(CSVRecord record: parser){
                Map map = new HashMap();
                map.put("cusip",record.get("Cusip"));
                map.put("quantity",Integer.parseInt(record.get("Quantity")));
                map.put("price",Float.parseFloat(record.get("Price")));
                list.add(map);
            }
            if(title == null){
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                title = dateFormat.format(new Date());
            }
            portfolioInfo.setList(list);
            portfolioInfo.setTitle(title);
            portfolioInfo.setAccountId(id);
            accountQuery.addPortfolio(portfolioInfo);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    //add by zj 16/12/15
    public ResultBean loadPortfolio(String id){
        return ResultBean.getSuccessResult(accountQuery.loadPortfolio(id));
    }

    //add by zj 16/12/19
    public void updatePortfolio(PortfolioInfo portfolioInfo){
        accountQuery.updatePortfolio(portfolioInfo);
    }

    //add by zj 16/12/30
    public ResultBean getAccounts(){
        List<CompanyInfo> companyInfos = accountQuery.getAccountNames();
        List<AccountInfo> accountInfos = new ArrayList<>();
        List<Map> accounts = new ArrayList<>();
        for(CompanyInfo companyInfo: companyInfos)
        {
            if(companyInfo.getAccountInfos() != null)
                accountInfos.addAll(companyInfo.getAccountInfos());
        }
        for(AccountInfo accountInfo: accountInfos){
            Map<String, String> accountMap = new HashMap();
            accountMap.put("id", accountInfo.getId());
            accountMap.put("text", accountInfo.getLongName());
            accounts.add(accountMap);
        }
        return ResultBean.getSuccessResult(accounts);
    }

    public ResultBean getPortfolioTitles(String[] accountIds){
        List<PortfolioInfo> portfolioInfos = accountQuery.getPortfoliosTitles(accountIds);
        List<Map> portfolios = new ArrayList<>();
        for(PortfolioInfo portfolioInfo: portfolioInfos){
            Map<String, String> portfolioMap = new HashMap<>();
            portfolioMap.put("id", portfolioInfo.getId());
            portfolioMap.put("text", portfolioInfo.getTitle());
            portfolioMap.put("email", portfolioInfo.getEmail());
            portfolios.add(portfolioMap);
        }
        return ResultBean.getSuccessResult(portfolios);
    }

    // add by zj 17/01/10
    public ResultBean loadMatchInfo(List<String> portfolioTitles){
        String json = "[";
        for(String title: portfolioTitles){
            PortfolioInfo portfolioInfo = accountQuery.findPortfolioInfoByTitle(title);
            String accountId = portfolioInfo.getAccountId();
            AccountInfo accountInfo = accountQuery.findAccountByAccountId(accountId);
            json += "{\"accountName\":\"" + accountInfo.getLongName() + "\",\"title\":\"" + portfolioInfo.getTitle() +"\",\"email\":\"" + portfolioInfo.getEmail() + "\"}"+",";
        }
        json = json.substring(0,json.length()-1);
        json += "]";
        return ResultBean.getSuccessResult(json);
    }
}
