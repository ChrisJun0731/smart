package com.sumridge.smart.dao;

import com.sumridge.smart.entity.TeamInfo;
import com.sumridge.smart.entity.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by liu on 16/3/29.
 */
public interface TeamInfoRepository extends MongoRepository<TeamInfo,String> {
    TeamInfo findByTeamId(String teamId);
}
