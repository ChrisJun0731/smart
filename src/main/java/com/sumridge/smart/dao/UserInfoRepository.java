package com.sumridge.smart.dao;

import com.sumridge.smart.entity.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by liu on 16/3/29.
 */
public interface UserInfoRepository extends MongoRepository<UserInfo,String> {
    UserInfo findByEmail(String email);

}
