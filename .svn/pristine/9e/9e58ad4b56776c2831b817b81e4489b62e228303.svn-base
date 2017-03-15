package com.sumridge.smart.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by liu on 16/6/23.
 */
@Configuration
@EnableMongoRepositories(
        basePackages = "com.sumridge.smart.dao",
        mongoTemplateRef = "mongoTemplate"
)
@Import(MongoDBConfig.class)
public class MongoTemplateConfig {


    @Primary
    @Bean
    @Qualifier("mongoDbFactory")
    public MongoDbFactory mongoDbFactory(@Value("${spring.data.mongodb.main.database}") String database, @Qualifier("primaryDataSource") MongoClient mongo) {
        MongoDbFactory factory = new SimpleMongoDbFactory(mongo, database);
        return factory;
    }
    @Primary
    @Bean
    public MongoTemplate mongoTemplate(@Qualifier("mongoDbFactory") MongoDbFactory factory) {


        return new MongoTemplate(factory);
    }
}
