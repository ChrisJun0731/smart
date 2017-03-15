package com.sumridge.smart.dao;

import com.sumridge.smart.entity.TaskInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by liu on 16/4/6.
 */
public interface TaskInfoRepository extends MongoRepository<TaskInfo,String> {
    Long countByOwnerIdAndStatus(String ownerId, String status);
}
