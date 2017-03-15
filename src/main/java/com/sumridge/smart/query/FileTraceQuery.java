package com.sumridge.smart.query;

import com.sumridge.smart.entity.FileTraceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

/**
 * Created by liu on 16/8/23.
 */
@Component
public class FileTraceQuery {
    @Autowired
    private MongoTemplate mongoTemplate;

    public boolean isExsit(String md5, String optType, String optResult) {
        Query query = Query.query(Criteria.where("md5").is(md5).and("optType").is(optType).and("optResult").is(optResult));
        return mongoTemplate.exists(query, FileTraceInfo.class);
    }
}
