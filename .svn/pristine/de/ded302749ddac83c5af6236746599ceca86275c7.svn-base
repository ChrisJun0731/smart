package com.sumridge.smart.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.sumridge.smart.bean.ResultBean;
import com.sumridge.smart.bean.TaskStatus;
import com.sumridge.smart.domain.CurrentUser;
import com.sumridge.smart.entity.TaskInfo;
import com.sumridge.smart.service.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {
	
	@Autowired
	public TaskService taskService;
	
	@RequestMapping(value="/save",method = RequestMethod.POST)
	public ResultBean saveTask(Authentication authentication,@RequestBody TaskInfo taskInfo){
		//TODO check user
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if(user != null){
        	Date today = Calendar.getInstance().getTime();
        	taskInfo.setCreateTime(today);
        	Date startTime = taskInfo.getStartTime();
        	Date endTime = taskInfo.getEndTime();
        	if(endTime != null){
        		Calendar calInstance = Calendar.getInstance();
        		calInstance.setTime(endTime);
        		calInstance.add(Calendar.DATE, 1);
        		endTime = calInstance.getTime();
        		taskInfo.setEndTime(endTime);
        	}
        	if(startTime == null || today.before(startTime)){
        		taskInfo.setStatus(TaskStatus.ToDo);
        	}else if(endTime == null || today.before(endTime)){
        		taskInfo.setStatus(TaskStatus.Processing);
        	}else if(today.after(endTime)){
        		taskInfo.setStatus(TaskStatus.Cancel);
        	}
        	taskInfo.setOwnerId(user.getUserInfo().getId());
        	ResultBean bean = taskService.saveTaskInfo(taskInfo);
        	return bean;
        }else{
        	ResultBean bean = new ResultBean();
        	return bean;
        }
	}
	
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public ResultBean getTaskList(Authentication authentication){
		CurrentUser user = (CurrentUser) authentication.getPrincipal();
		ResultBean bean = new ResultBean();
		if(user != null && user.getUserInfo() != null){
			List<TaskInfo> list = taskService.getTaskList(user.getUserInfo());
			bean.setData(list);
			bean.setSuccess(true);
		}
		return bean;
	}
	
	@RequestMapping(value="/markup",method = RequestMethod.GET)
	public ResultBean markup(Authentication authentication,HttpServletRequest request){
		String id = request.getParameter("id");
		CurrentUser user = (CurrentUser) authentication.getPrincipal();
		return taskService.toggleMarkup(user.getUserInfo(),id);
	}
	
	@RequestMapping(value="/cancel",method = RequestMethod.GET)
	public ResultBean cancel(Authentication authentication,HttpServletRequest request){
		String id = request.getParameter("id");
		CurrentUser user = (CurrentUser) authentication.getPrincipal();
		return taskService.cancelTask(user.getUserInfo(),id);
	}
	
	@RequestMapping(value="/load",method = RequestMethod.GET)
	public ResultBean load(Authentication authentication,HttpServletRequest request){
		String id = request.getParameter("id");
		CurrentUser user = (CurrentUser) authentication.getPrincipal();
		return taskService.loadTask(user.getUserInfo(),id);
	}


	//add by zj 17/01/09
	@RequestMapping(value="/taskFilter", method= RequestMethod.GET)
	public ResultBean taskFilter(Authentication authentication, @RequestParam("status") String status){
		CurrentUser currentUser = (CurrentUser)authentication.getPrincipal();
		if(currentUser != null){
			return taskService.statusFilter(status);

		}
		else{
			return new ResultBean();
		}
	}


	@RequestMapping(value="/finishTask", method = RequestMethod.GET)
	public void finishTask(Authentication authentication, @RequestParam("id") String id){
		CurrentUser currentUser = (CurrentUser)authentication.getPrincipal();
		if(currentUser != null){
			taskService.finishTask(id);
		}
	}

	// add by zj 17/01/13
	@RequestMapping(value="/addActivityInfo", method=RequestMethod.POST)
	public void addActivityInfo(Authentication authentication, @RequestBody TaskInfo taskInfo){
		CurrentUser currentUser = (CurrentUser)authentication.getPrincipal();
		if(currentUser != null){
			String userId = currentUser.getUserInfo().getId();
			taskService.addActivityInfo(userId, taskInfo);
		}
	}
}
