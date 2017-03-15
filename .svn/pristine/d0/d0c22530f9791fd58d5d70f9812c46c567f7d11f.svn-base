package com.sumridge.smart.query;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.sumridge.smart.entity.ChatUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by liu on 16/6/23.
 */
@Component
public class ChatUserQuery {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatUserQuery.class);
    @Autowired
    @Qualifier("chatMongoTemplate")
    private MongoTemplate chatMongoTemplate;

    public String queryTokenByUserId(String userId) {
        DBObject dbObject = chatMongoTemplate.getCollection("users").findOne(userId, new BasicDBObject("services.iframe.token", "1"));
        if (dbObject != null && dbObject.containsField("services")) {

            DBObject services = (DBObject) dbObject.get("services");
            DBObject iframe = (DBObject) services.get("iframe");
            LOGGER.info("query:" + iframe.get("token").toString());
            return iframe.get("token").toString();

        } else {
            return null;
        }
    }

    public void insertChatUser(ChatUserInfo chatUser) {
        chatMongoTemplate.save(chatUser,"users");
    }
}
