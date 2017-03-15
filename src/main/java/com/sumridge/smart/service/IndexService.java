package com.sumridge.smart.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by liu on 16/8/10.
 */
@Service
public class IndexService {

    @Autowired
    @Qualifier("elasticClient")
    private Client client;

    public void createIndex(String json, String id) {
        client.prepareIndex("crm", "trade", id).setSource(json).get();

    }
    @Async
    public void createIndex(Object object, String id) {
        client.prepareIndex("crm", "trade", id).setSource(JSON.toJSONString(object, SerializerFeature.UseISO8601DateFormat)).get();
    }
}
