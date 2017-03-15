package com.sumridge.smart.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sumridge.smart.entity.AppointmentInfo;

public interface AppointmentInfoRepository  extends MongoRepository<AppointmentInfo,String>{

}
