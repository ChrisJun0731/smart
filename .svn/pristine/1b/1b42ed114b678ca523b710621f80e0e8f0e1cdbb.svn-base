package com.sumridge.smart.query;

import com.mongodb.BasicDBObject;
import com.sumridge.smart.bean.AccountAggBean;
import com.sumridge.smart.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * Created by liu on 16/4/13.
 */
@Component
public class UserQuery {
    @Autowired
    private MongoTemplate mongoTemplate;

    public UserInfo getBaseUserInfo(String id) {

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        query.fields().include("firstName").include("lastName").include("photo");

        return mongoTemplate.findOne(query, UserInfo.class);
    }

    public UserInfo getUserInfoByEmail(String email) {

        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));

        query.fields().include("firstName").include("lastName").include("_id");

        return mongoTemplate.findOne(query, UserInfo.class);
    }


    public List<AccountAggBean> queryList(Set<String> teamIdSet) {
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.sort(Sort.Direction.ASC,"initial"),Aggregation.match(Criteria.where("teamIdSet").in(teamIdSet)),
                Aggregation.group("initial").push(new BasicDBObject("firstName", "$firstName").append("lastName", "$lastName").append("description", "$description").append("id", "$_id")).as("accounts"));

        AggregationResults<AccountAggBean> result = mongoTemplate.aggregate(aggregation, UserInfo.class, AccountAggBean.class);

        return result.getMappedResults();
    }

    public void addTeamSetId(String teamId,String id) {

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        Update update = new Update().addToSet("teamIdSet", teamId);

        mongoTemplate.updateFirst(query, update, UserInfo.class);

    }

    public void removeTeamId(String id, String teamId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        Update update = new Update().pull("teamIdSet", teamId);

        mongoTemplate.updateFirst(query, update, UserInfo.class);
    }

    public UserInfo getIdBySale(String salesId) {
        if(salesId != null) {
            Query query = new Query();
            query.addCriteria(Criteria.where("userId").is(salesId));

            query.fields().include("_id").include("firstName").include("lastName");

            UserInfo user = mongoTemplate.findOne(query, UserInfo.class);

            if(user != null) {
                return user;
            }
        }
        return null;
    }

    //add by zj
    public void addUserInfo(UserInfo userInfo)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("Uid").is(userInfo.getUId()));
        if(mongoTemplate.exists(query,"userInfo"))
        {
            Update update = new Update();
            update.set("firstName",userInfo.getFirstName());
            update.set("lastName",userInfo.getLastName());
            update.set("email",userInfo.getEmail());
            update.set("initial",userInfo.getInitial());
            update.set("password",userInfo.getPassword());
            update.set("teamIdSet",userInfo.getTeamIdSet());
            update.set("userId",userInfo.getUserId());
            mongoTemplate.updateFirst(query,update,"userInfo");
        }
        else
        {
            mongoTemplate.insert(userInfo,"userInfo");
        }
    }

}
