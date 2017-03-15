package com.sumridge.smart.dao;

import com.sumridge.smart.entity.UserInvite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by liu on 16/11/16.
 */
public interface UserInviteRepository extends MongoRepository<UserInvite,String> {

}
