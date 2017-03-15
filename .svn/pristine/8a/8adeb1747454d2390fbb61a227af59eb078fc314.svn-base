package com.sumridge.smart.dao;

import com.sumridge.smart.entity.BoardInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by liu on 16/4/6.
 */
public interface BoardInfoRepository extends MongoRepository<BoardInfo,String> {
    BoardInfo findByOwnerId(String ownerId);
}
