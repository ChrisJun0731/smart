package com.sumridge.smart.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.net.UnknownHostException;

/**
 * Created by liu on 16/6/23.
 */
@Configuration
public class MongoDBConfig {

    @Bean(name = "primaryDataSource")
    @Qualifier("primaryDataSource")
    public MongoClient primaryMongoSource(@Value("${spring.data.mongodb.main.host}") String host, @Value("${spring.data.mongodb.main.port}") int port) throws UnknownHostException {

        return new MongoClient(host, port);

    }

    @Bean(name = "chatDataSource")
    @Qualifier("secondaryDataSource")
    public MongoClient chatDataDataSource(@Value("${spring.data.mongodb.chat.host}") String host, @Value("${spring.data.mongodb.chat.port}") int port) throws UnknownHostException {

        return new MongoClient(host, port);

    }

    @Bean
    @Qualifier("chatMongoTemplate")
    public MongoTemplate createChatTemplate(@Qualifier("chatDataSource") MongoClient mongo) {

        MongoDbFactory factory = new SimpleMongoDbFactory(mongo, "meteor");

        return new MongoTemplate(factory);
    }



}
