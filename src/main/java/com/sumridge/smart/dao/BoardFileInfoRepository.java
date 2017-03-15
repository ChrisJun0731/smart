package com.sumridge.smart.dao;

import com.sumridge.smart.entity.BoardFileInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by liu on 16/4/20.
 */
public interface BoardFileInfoRepository extends MongoRepository<BoardFileInfo,String> {
}
