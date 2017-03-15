package com.sumridge.smart.query;

import com.mongodb.BasicDBObject;
import com.sumridge.smart.bean.ContactAggBean;
import com.sumridge.smart.entity.Contact;
import com.sumridge.smart.entity.SaleInfo;
import com.sumridge.smart.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhujun on 2016/10/12.
 */
@Repository
public class CommonQuery {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<SaleInfo> getSortedSaleList(int currentPage, int pageSize, String orderName, String order){
        Sort sort = new Sort(Sort.Direction.ASC,orderName);
        if(order.equals("desc"))
            sort = new Sort(Sort.Direction.DESC,orderName);
        Query query = new Query().with(sort);
        query.skip(currentPage*pageSize);
        return mongoTemplate.find(query,SaleInfo.class);
    }

    public List<ContactAggBean> getSortedContactList(UserInfo userInfo, String orderName, String order){
        Aggregation aggregation = null;
        if("asc".equals(order)){
            aggregation = Aggregation.newAggregation(Aggregation.match(new Criteria().orOperator(Criteria.where("visibleList").elemMatch((Criteria.where("visType").is("private").and("visId").is(userInfo.getId()))),
                    Criteria.where("visibleList.visType").is("public"),
                    Criteria.where("visibleList").elemMatch(Criteria.where("visType").is("team").and("visId").in(userInfo.getTeamIdSet())))),
                    Aggregation.sort(Sort.Direction.ASC,orderName),
                    Aggregation.group("initial").push(new BasicDBObject("firstName","$firstName").append("lastName","$lastName")
                    .append("id","$_id").append("title","$title")).as("contacts"),
                    Aggregation.sort(Sort.Direction.ASC,"_id"));
        }
        else{
            aggregation = Aggregation.newAggregation(Aggregation.match(new Criteria().orOperator(Criteria.where("visibleList").elemMatch((Criteria.where("visType").is("private").and("visId").is(userInfo.getId()))),
                    Criteria.where("visibleList.visType").is("public"),
                    Criteria.where("visibleList").elemMatch(Criteria.where("visType").is("team").and("visId").in(userInfo.getTeamIdSet())))),
                    Aggregation.sort(Sort.Direction.DESC,orderName),
                    Aggregation.group("initial").push(new BasicDBObject("firstName","$firstName").append("lastName","$lastName")
                            .append("id","$_id").append("title","$title")).as("contacts"),
                    Aggregation.sort(Sort.Direction.ASC,"_id"));
        }
        AggregationResults<ContactAggBean> result = mongoTemplate.aggregate(aggregation,Contact.class,ContactAggBean.class);
        return result.getMappedResults();
    }
}
