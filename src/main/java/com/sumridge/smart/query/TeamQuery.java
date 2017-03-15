package com.sumridge.smart.query;

import com.mongodb.BasicDBObject;
import com.sumridge.smart.bean.AccountAggBean;
import com.sumridge.smart.bean.MapBean;
import com.sumridge.smart.bean.TeamNameBean;
import com.sumridge.smart.entity.TeamInfo;
import com.sumridge.smart.entity.UserInfo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Created by liu on 16/5/23.
 */
@Component
public class TeamQuery {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<AccountAggBean> queryList(Set<String> teamIdSet) {
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(Criteria.where("_id").in(teamIdSet)),
                Aggregation.group("initial").push(new BasicDBObject("teamName", "$teamName").append("description", "$description").append("id", "$_id")).as("accounts"));

        AggregationResults<AccountAggBean> result = mongoTemplate.aggregate(aggregation, TeamInfo.class, AccountAggBean.class);

        return result.getMappedResults();
    }

    public void updateTeamInfo(TeamInfo info) {

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(info.getId()));

        Update update = Update.update("teamName",info.getTeamName()).set("description", info.getDescription())
                .set("initial",info.getInitial()).set("tags", info.getTags());

        mongoTemplate.updateFirst(query, update, TeamInfo.class);
    }

    public void saveMember(MapBean bean, String teamId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(teamId).and("userList._id").is(new ObjectId(bean.getId())));

        Update update = Update.update("userList.$", bean);

        mongoTemplate.updateFirst(query, update, TeamInfo.class);
    }

    public void pushMember(MapBean bean, String teamId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(teamId));

        Update update = new Update().push("userList", bean);

        mongoTemplate.updateFirst(query, update, TeamInfo.class);
    }

    public MapBean getMember(String id, String teamId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(teamId).and("userList._id").is(new ObjectId(id)));
        query.fields().include("userList.$");
        TeamInfo info = mongoTemplate.findOne(query, TeamInfo.class);
        if(info != null && !info.getUserList().isEmpty()) {
            return info.getUserList().stream().findFirst().get();
        } else {
            return null;
        }

    }

    public void removeMember(MapBean member, String teamId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(teamId));

        Update update = new Update().pull("userList", new BasicDBObject("_id", new ObjectId(member.getId())));

        mongoTemplate.updateFirst(query, update, TeamInfo.class);
    }

    public List<TeamNameBean> queryNameList() {

        Query query = new Query();
        query.fields().include("_id").include("teamName");
        return mongoTemplate.find(query, TeamNameBean.class,"teamInfo");
    }

    //add by zj 16/9/13
    public String queryId(String teamName)
    {
        Query query = new Query(Criteria.where("teamName").is(teamName));
        TeamInfo teamInfo = mongoTemplate.findOne(query,TeamInfo.class,"teamInfo");
        return teamInfo.getId();
    }
}
