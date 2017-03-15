package com.sumridge.smart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.sumridge.smart.bean.ResultBean;
import com.sumridge.smart.domain.CurrentUser;
import com.sumridge.smart.entity.AppointmentInfo;
import com.sumridge.smart.entity.UserInfo;
import com.sumridge.smart.service.AppointmentService;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
	
	@Autowired
	public AppointmentService appointmentService;
	
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public ResultBean getAppoinmentList(Authentication authentication){
		CurrentUser user = (CurrentUser) authentication.getPrincipal();
		ResultBean bean = new ResultBean();
		if(user != null && user.getUserInfo() != null){
			List<AppointmentInfo> list = appointmentService.getAppointmentList(user.getUserInfo());
			bean.setData(list);
			bean.setSuccess(true);
		}
		return bean;
	}
	
	@RequestMapping(value="/info",method = RequestMethod.POST)
	public ResultBean addAppointment(Authentication authentication,@RequestBody AppointmentInfo appointmentInfo){
		CurrentUser user = (CurrentUser) authentication.getPrincipal();
		ResultBean bean = new ResultBean();
		if(user != null && user.getUserInfo() != null){
			UserInfo userInfo = user.getUserInfo();
			appointmentInfo.setOwnerId(userInfo.getId());
			return appointmentService.addAppointment(appointmentInfo);
		}
		bean.setSuccess(false);
		return bean;
	}

    @RequestMapping(value="/info/{id}",method = RequestMethod.DELETE)
    public ResultBean removeAppointment(Authentication authentication, @PathVariable("id") String id){
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        ResultBean bean = new ResultBean();
        if(user != null && user.getUserInfo() != null){
            return appointmentService.removeAppointment(user.getUserInfo(), id);
        }
        return bean;
    }

    @RequestMapping(value="/recent",method = RequestMethod.GET)
    public ResultBean getRecentAppoinmentList(Authentication authentication){
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        ResultBean bean = new ResultBean();
        if(user != null && user.getUserInfo() != null){
            List<AppointmentInfo> list = appointmentService.getRecentAppoinmentList(user.getUserInfo());
            bean.setData(list);
            bean.setSuccess(true);
        }
        return bean;
    }

    // add by zj 17/01/16
	@RequestMapping(value="/addActivityInfo", method=RequestMethod.POST)
	public void addActivityInfo(Authentication authentication, @RequestBody AppointmentInfo appointmentInfo){
		CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
		if(currentUser != null){
			UserInfo userInfo = currentUser.getUserInfo();
			appointmentService.addActivityInfo(userInfo.getId(), appointmentInfo);
		}
	}
	

}
