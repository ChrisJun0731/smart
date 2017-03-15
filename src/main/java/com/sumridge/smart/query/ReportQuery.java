package com.sumridge.smart.query;

import com.sumridge.smart.entity.ReportInfo;
import com.sumridge.smart.entity.SaleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * Created by liu on 16/8/4.
 */
@Component
public class ReportQuery {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<ReportInfo> getSaleList(int limit, Set<String> teamIdSet) {

        Query query = new Query();
        query.addCriteria(Criteria.where("visibles.visType").is("T").and("visibles.visId").in(teamIdSet));
        query.limit(limit);
        query.fields().include("id").include("title").include("reportUrl");
        return mongoTemplate.find(query, ReportInfo.class);
    }

}
