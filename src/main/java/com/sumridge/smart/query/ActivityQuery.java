package com.sumridge.smart.query;

import com.sumridge.smart.entity.ActivityInfo;
import com.sumridge.smart.entity.CommentInfo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by liu on 16/4/12.
 */
@Component
public class ActivityQuery {
    @Autowired
    private MongoTemplate mongoTemplate;


    public List<ActivityInfo> getHomeActivity(String homeId, Set<String> followings, Date lastTime) {

        //fetch home subscribe activity from db
        Query query = new Query();
        Criteria queryCriteria = new Criteria();

        if(followings != null && followings.size()>0){
            queryCriteria = queryCriteria.orOperator(Criteria.where("boardId").in(followings).and("level").is("public"), Criteria.where("boardId").is(homeId));
        } else {
            queryCriteria = Criteria.where("boardId").is(homeId);
        }
        query.addCriteria(queryCriteria);
        query.fields().include("_id").include("eventType").include("eventDate").include("eventUser").include("eventMsg").include("boardId").include("commentCount").include("commentInfoList");
        query.with(new Sort(Sort.Direction.DESC,"eventDate"));
        query.limit(20);
        List<ActivityInfo> list = mongoTemplate.find(query, ActivityInfo.class);
        return list;
    }

    public List<ActivityInfo> getBoardActivity(String boardId, Date lastTime) {
        Query query = new Query();
        query.addCriteria(Criteria.where("boardId").is(boardId).and("level").is("public"));
        query.fields().include("_id").include("eventType").include("eventDate").include("eventUser").include("eventMsg").include("boardId").include("commentCount");
        query.with(new Sort(Sort.Direction.DESC,"eventDate"));
        query.limit(20);
        List<ActivityInfo> list = mongoTemplate.find(query, ActivityInfo.class);
        return list;
    }

    public void pushComment(CommentInfo commentInfo, String id){
        Update update = new Update();
        update.push("commentInfoList",commentInfo);
        update.inc("commentCount",1);
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        mongoTemplate.updateFirst(query,update,ActivityInfo.class);
    }
}
