package com.sumridge.smart.query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.sumridge.smart.bean.TaskStatus;
import com.sumridge.smart.entity.AppointmentInfo;
import com.sumridge.smart.entity.UserInfo;

@Component
public class AppointmentQuery {
	@Autowired
    private MongoTemplate mongoTemplate;
	
	public List<AppointmentInfo> queryAppointment(UserInfo userInfo){
		Query query = new Query();
		query.addCriteria(Criteria.where("ownerId").is(userInfo.getId()).and("status").ne(TaskStatus.Cancel.getValue()));
		query.with(new Sort(new Order(Direction.ASC, "endTime")));
		return mongoTemplate.find(query, AppointmentInfo.class);
	}


    public List<AppointmentInfo> queryRecentAppointment(String id, Date start, Date end) {

        Query query = new Query();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
        try {

            final Date to = dateFormat.parse(dateFormat.format(end));
            final Date from = dateFormat.parse(dateFormat.format(start));
            query.addCriteria(Criteria.where("ownerId").is(id).and("status").ne(TaskStatus.Cancel.getValue())
                    .and("startTime").gte(from).lte(to));
            query.with(new Sort(new Order(Direction.ASC, "endTime")));
            return mongoTemplate.find(query, AppointmentInfo.class);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }
}
