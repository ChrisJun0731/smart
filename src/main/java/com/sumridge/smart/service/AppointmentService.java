package com.sumridge.smart.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.sumridge.smart.dao.ActivityInfoRepository;
import com.sumridge.smart.entity.ActivityInfo;
import com.sumridge.smart.entity.BoardInfo;
import com.sumridge.smart.query.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sumridge.smart.bean.ResultBean;
import com.sumridge.smart.dao.AppointmentInfoRepository;
import com.sumridge.smart.entity.AppointmentInfo;
import com.sumridge.smart.entity.UserInfo;
import com.sumridge.smart.query.AppointmentQuery;

@Service
public class AppointmentService {
	
	@Autowired
	AppointmentQuery appointmentQuery;

	@Autowired
	TaskQuery taskQuery;
	
	@Autowired
	AppointmentInfoRepository appointmentRepository;

	@Autowired
	ActivityInfoRepository activityInfoRepository;


	
	public List<AppointmentInfo> getAppointmentList(UserInfo userInfo){
		if(userInfo == null){
			return new ArrayList<AppointmentInfo>();
		}
		List<AppointmentInfo> list = appointmentQuery.queryAppointment(userInfo);
		return list;
	}
	
	public ResultBean addAppointment(AppointmentInfo appointmentInfo){
		appointmentRepository.save(appointmentInfo);
		ResultBean result = new ResultBean();
		result.setSuccess(true);
		return result;
	}


    public ResultBean removeAppointment(UserInfo userInfo, String id) {
        appointmentRepository.delete(id);

        return ResultBean.getSuccessResult();
    }

    public List<AppointmentInfo> getRecentAppoinmentList(UserInfo userInfo) {
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DAY_OF_MONTH, 7);
        List<AppointmentInfo> list = appointmentQuery.queryRecentAppointment(userInfo.getId(), startDate.getTime(), endDate.getTime());
        return list;
    }

    //add by zj 17/01/16
	public void addActivityInfo(String userId, AppointmentInfo appointmentInfo){
		Date date = new Date();
		ActivityInfo activityInfo = new ActivityInfo();
		BoardInfo boardInfo = taskQuery.findBoardInfoByUserId(userId);
		activityInfo.setEventUser(userId);
		activityInfo.setEventDate(date);
		activityInfo.setEventType("event");
		activityInfo.setBoardId(boardInfo.getId());
		String eventMsg = "{\"title\":\""+ appointmentInfo.getTitle() + "\",\"description\":\""+appointmentInfo.getDescription()+"\"}";
		activityInfo.setEventMsg(eventMsg);
		activityInfoRepository.save(activityInfo);
	}
}
