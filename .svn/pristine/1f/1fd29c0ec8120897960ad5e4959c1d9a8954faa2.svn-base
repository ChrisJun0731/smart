package com.sumridge.smart.service;

import com.sumridge.smart.bean.BestMktDataBean;
import com.sumridge.smart.bean.CommonAggBean;
import com.sumridge.smart.bean.ResultBean;
import com.sumridge.smart.dao.MatchInfoRepository;
import com.sumridge.smart.entity.MatchInfo;
import com.sumridge.smart.entity.PortfolioInfo;
import com.sumridge.smart.entity.UserInfo;
import com.sumridge.smart.query.ContactQuery;
import com.sumridge.smart.query.CusipMktDataQuery;
import com.sumridge.smart.query.MatchQuery;
import com.sumridge.smart.query.UserQuery;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by liu on 16/11/2.
 */
@Service
public class MatchService {


    @Autowired
    private MatchQuery matchQuery;

    @Autowired
    private MatchInfoRepository matchInfoRepository;

    @Autowired
    private ContactQuery contactQuery;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserQuery userQuery;

    @Autowired
    private CusipMktDataQuery cusipMktDataQuery;

    public ResultBean getMatchList(UserInfo userInfo) {

        return ResultBean.getSuccessResult(matchQuery.getMatchInfoList(userInfo.getId()));
    }

    public ResultBean addSimpleMatch(UserInfo userInfo, MatchInfo bean) {
        if (bean.getId() == null) {

            bean.setCreateDate(new Date());
            bean.setCreator(userInfo.getId());
            bean.setSent(true);
            bean.setStatus("open");
        }


        alertMatchUser(bean, userInfo);
        matchInfoRepository.save(bean);
        return ResultBean.getSuccessResult();
    }

    private void alertMatchUser(MatchInfo bean, UserInfo userInfo) {

        Map<String, Object> params = new HashMap<>();

        params.put("des", bean.getContent());
        params.put("sendUser", userInfo.getFirstName() + " " + userInfo.getLastName());
        params.put("sendCompany", userInfo.getCompany());
        params.put("matchList", bean.getSalesMap());
        Set<String> toList = new HashSet<>();
        bean.getCustomer().forEach(emailBean -> {
            toList.add(emailBean.getEmail());
        });
        String content = mailService.buildMailContent("match-email", params);
        mailService.sendEmail(StringUtil.join(toList.toArray(), ","), bean.getTitle(), content);
    }

    public ResultBean getNameList(UserInfo userInfo, String query) {


        return ResultBean.getSuccessResult(contactQuery.queryNameList(query));
    }

    public ResultBean getAggSuggestionList(UserInfo userInfo, String filter) {

        List<CommonAggBean> list = matchQuery.getBaseAggList();
        list.forEach(commonAggBean -> {
            commonAggBean.getDetail().forEach(stringStringMap -> {
                UserInfo info = userQuery.getBaseUserInfo(stringStringMap.get("creator"));
                stringStringMap.put("fromName", info.getFirstName() + " " + info.getLastName());
                stringStringMap.put("fromPhoto", info.getPhoto());
            });

        });
        return ResultBean.getSuccessResult(list);
    }

    //query portfolio from email add by zj 03/09\
    public List<String> queryAccountIdByEmail(String email) {
        List<String> accountIds = matchQuery.queryAccountIdByEmail(email);
        return accountIds;
    }

    //get portfolio info via accountId
    public ResultBean queryPortfolioByAccountId(List<String> accountIds) {
        List<PortfolioInfo> portfolioInfoList = matchQuery.queryPortfolioByAccountId(accountIds);
        return ResultBean.getSuccessResult(portfolioInfoList);
    }

    //query cusip list via portfolioId
    public ResultBean queryCusipListByPortfolioId(String[] portfolioIds) {
        List<PortfolioInfo> portfolioInfos = new ArrayList<>();
        for (String portfolioId : portfolioIds) {
            PortfolioInfo portfolioInfo = matchQuery.getCusipList(portfolioId);
            portfolioInfos.add(portfolioInfo);
        }
        return ResultBean.getSuccessResult(portfolioInfos);
    }

    //call the getMktDataSimpleByCusip Query add by zj 17/03/10
    public ResultBean getMktDataSimpleByCusip(Set<String> cusipSet){
        List<BestMktDataBean> bestMktDataBeanList = cusipMktDataQuery.getMktDataSimpleByCusip(cusipSet);
        return ResultBean.getSuccessResult(bestMktDataBeanList);
    }
}
