package com.sumridge.smart.query;

import com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.sumridge.smart.bean.ContactAggBean;
import com.sumridge.smart.bean.EmailBean;
import com.sumridge.smart.bean.NameBean;
import com.sumridge.smart.bean.FieldBean;
import com.sumridge.smart.entity.AccountMapping;
import com.sumridge.smart.entity.CompanyInfo;
import com.sumridge.smart.entity.Contact;
import com.sumridge.smart.entity.UserInfo;
import com.sun.corba.se.pept.transport.ContactInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liu on 16/5/11.
 */
@Component
public class ContactQuery {
    @Autowired
    private MongoTemplate mongoTemplate;

    public Contact getContactByMap(String oppTrader, String brokerInitials, String ecnSource) {

        Query query = Query.query(Criteria.where("salesMap._id").is(oppTrader).and("salesMap.account").is(brokerInitials).and("salesMap.source").is(ecnSource));
        query.fields().include("_id").include("firstName").include("lastName");
        Contact con = mongoTemplate.findOne(query, Contact.class);
        if(con != null) {
            return con;
        } else {
            return null;
        }


    }

    public String getContactName(String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        query.fields().include("firstName").include("lastName");
        Contact con = mongoTemplate.findOne(query, Contact.class);
        if(con != null) {
            return con.getFirstName()+" "+con.getLastName();
        } else {
            return null;
        }

    }

    public List<EmailBean> queryNameList(String text) {
        Query query = new Query();
        query.fields().include("_id").include("email");
        return mongoTemplate.find(query, EmailBean.class,"contact");
    }

    //add by zj 16/9/15
    public void addContactInfo(Contact contact)
    {
        Query query = new Query(Criteria.where("email").is(contact.getEmail()));
        if(mongoTemplate.exists(query,"contact"))
        {
            Update update = new Update();
            update.set("firstName",contact.getFirstName());
            update.set("lastName",contact.getLastName());
            update.set("phone",contact.getPhone());
            update.set("initial",contact.getInitial());
            update.set("visibleList",contact.getVisibleList());
            mongoTemplate.updateFirst(query,update,"contact");
        }
        else
        {
            mongoTemplate.insert(contact,"contact");
        }
    }

    public void updateContactInfo(Contact info, AccountMapping mapping) {
        Query query = new Query();
        query.addCriteria(Criteria.where("firstName").is(info.getFirstName()).and("lastName").is(info.getLastName()));
        Update update = new Update();
        update.addToSet("salesMap",mapping);
        mongoTemplate.updateFirst(query,update,"contact");
    }

    //add by zj 16/11/04
    public void saveFormField(List<FieldBean> fields){
        mongoTemplate.dropCollection("fields");
        mongoTemplate.insert(fields,"fields");
    }

    //add bu zj 17/01/03
    public List<Contact> getContactNames(){
        Query query = new Query();
        List<Contact> contacts = mongoTemplate.find(query, Contact.class);
        return contacts;
    }

    //query contact initial collection add by zj 17/03/08
    public List<Map> getInitials(){
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.group("initial"),Aggregation.sort(Sort.Direction.ASC,"_id"));
        AggregationResults<Map> result = mongoTemplate.aggregate(aggregation, Contact.class, Map.class);
        return result.getMappedResults();
    }

    //query contacts of initial
    public List<ContactAggBean> getInitialContacts(String initial, UserInfo userInfo, int limit) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("initial").is(initial)),
                Aggregation.match(new Criteria().orOperator(Criteria.where("visibleList").elemMatch((Criteria.where("visType").is("private").and("visId").is(userInfo.getId()))),
                        Criteria.where("visibleList.visType").is("public"),
                        Criteria.where("visibleList").elemMatch(Criteria.where("visType").is("team").and("visId").in(userInfo.getTeamIdSet())),
                        Criteria.where("visibleList").elemMatch(Criteria.where("isType").is("company").and("visId").is(userInfo.getCompany())))),
                Aggregation.limit(limit),
                Aggregation.group("initial").push(new BasicDBObject("firstName","$firstName").append("lastName","$lastName").append("id","$_id").append("title","$title")).as("contacts")
                );
        AggregationResults<ContactAggBean> result = mongoTemplate.aggregate(aggregation, Contact.class, ContactAggBean.class);
        return result.getMappedResults();
    }

}
