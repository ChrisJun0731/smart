package com.sumridge.smart.query;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.sumridge.smart.bean.CommonAggBean;
import com.sumridge.smart.entity.AccountInfo;
import com.sumridge.smart.entity.CompanyInfo;
import com.sumridge.smart.entity.MatchInfo;
import com.sumridge.smart.entity.PortfolioInfo;
import com.sun.xml.internal.ws.wsdl.writer.document.Port;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liu on 16/11/2.
 */
@Component
public class MatchQuery {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<MatchInfo> getMatchInfoList(String creator) {

        Query query = Query.query(Criteria.where("creator").is(creator));

        return mongoTemplate.find(query, MatchInfo.class);


    }

    public List<CommonAggBean> getBaseAggList() {

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("salesMap"),
                Aggregation.group("salesMap.cusip").push(new BasicDBObject("price", "$salesMap.price").append("quantity", "$salesMap.quantity")
                        .append("side", "$salesMap.side")
                        .append("creator","$creator")
                        .append("createDate","$createDate")).as("detail")
                .count().as("count"));

        AggregationResults<CommonAggBean> result = mongoTemplate.aggregate(aggregation, MatchInfo.class, CommonAggBean.class);

        return result.getMappedResults();
    }

    // query accountid by email add by zj 17/03/09
    public List<String> queryAccountIdByEmail(String email){
        DBObject queryObject = new BasicDBObject();
        queryObject.put("email", email);
        DBObject fields = new BasicDBObject();
        fields.put("_id", false);
        fields.put("accountInfos._id", true);
        Query query = new BasicQuery(queryObject, fields);
        CompanyInfo companyInfo = mongoTemplate.findOne(query, CompanyInfo.class);
        List<AccountInfo> accountInfos = companyInfo.getAccountInfos();
        List<String> accountIds = new ArrayList();
        if(accountInfos != null){
            for(AccountInfo accountInfo: accountInfos){
                accountIds.add(accountInfo.getId());
            }
        }
        return accountIds;
    }

    //query portfolio info by accountId add by zj 17/03/09
    public List<PortfolioInfo> queryPortfolioByAccountId(List<String> accountIds){
        List<PortfolioInfo> portfolioInfoList = new ArrayList();
        for(String accountId: accountIds){
            DBObject queryObject = new BasicDBObject();
            queryObject.put("accountId", accountId);
            DBObject fields = new BasicDBObject();
            fields.put("title", true);
            Query query = new BasicQuery(queryObject, fields);
            List<PortfolioInfo> portfolioInfos = mongoTemplate.find(query, PortfolioInfo.class, "portfolios");
            if(portfolioInfos != null){
                portfolioInfoList.addAll(portfolioInfos);
            }
        }
        return portfolioInfoList;
    }

    //query cusips via portfolioId add by zj 17/03/09
    public PortfolioInfo getCusipList(String portfolioId){
        DBObject queryObject = new BasicDBObject();
        queryObject.put("_id", portfolioId);
        DBObject fields = new BasicDBObject();
        fields.put("list", true);
        Query query = new BasicQuery(queryObject, fields);
        PortfolioInfo portfolioInfo = mongoTemplate.findOne(query, PortfolioInfo.class, "portfolios");
        return portfolioInfo;
    }

}
