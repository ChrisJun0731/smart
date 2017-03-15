package com.sumridge.smart.dao;

import com.sumridge.smart.entity.ReportInfo;
import com.sumridge.smart.entity.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by liu on 16/3/29.
 */
public interface ReportInfoRepository extends MongoRepository<ReportInfo,String> {

}
