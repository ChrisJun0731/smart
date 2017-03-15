package com.sumridge.smart.dao;

import com.sumridge.smart.entity.SaleInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by liu on 16/6/14.
 */
public interface SaleInfoRepository extends MongoRepository<SaleInfo,String> {
}
