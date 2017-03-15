package com.sumridge.smart.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.sumridge.smart.dao.ActivityInfoRepository;
import com.sumridge.smart.entity.ActivityInfo;
import com.sumridge.smart.entity.BoardInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.sumridge.smart.bean.ResultBean;
import com.sumridge.smart.bean.TaskStatus;
import com.sumridge.smart.dao.TaskInfoRepository;
import com.sumridge.smart.entity.TaskInfo;
import com.sumridge.smart.entity.UserInfo;
import com.sumridge.smart.query.TaskQuery;
import com.sumridge.smart.util.TimeUtil;

@Service
public class TaskService {
	
	@Autowired
	private TaskInfoRepository taskInfoRepository;
	
	@Autowired
	private TaskQuery taskQuery;

	@Autowired
	private ActivityInfoRepository activityInfoRepository;
	
	public ResultBean saveTaskInfo(TaskInfo taskInfo){
		taskInfoRepository.save(taskInfo);
		ResultBean rs = new ResultBean();
        rs.setSuccess(true);
        return rs;
	}
	
	public List<TaskInfo> getTaskList(UserInfo userInfo){
		if(userInfo == null){
			return new ArrayList<TaskInfo>();
		}
		List<TaskInfo> list = taskQuery.queryActiveTask(userInfo);
		Date now = Calendar.getInstance().getTime();
		long n = now.getTime();
		for(TaskInfo bean : list){
			Date endTime = bean.getEndTime();
			if(endTime != null && endTime.after(now)){
				long end = endTime.getTime();
				long distance = end - n;
				if(distance >= TimeUtil.YEAR_MILLI_SECONDS){
					long distanceYear = distance / TimeUtil.YEAR_MILLI_SECONDS;
					bean.setDistanceTime(distanceYear + (distanceYear > 1 ? " years" :" year"));
				}else if(distance >= TimeUtil.MONTH_MILLI_SECONDS){
					long distanceMonth = distance / TimeUtil.MONTH_MILLI_SECONDS;
					bean.setDistanceTime(distanceMonth + (distanceMonth > 1 ? " months" :" month"));
				}else if(distance >= TimeUtil.WEEK_MILLI_SECONDS){
					long distanceWeek = distance / TimeUtil.WEEK_MILLI_SECONDS;
					bean.setDistanceTime(distanceWeek + (distanceWeek > 1 ? " weeks" :" week"));
				}else if(distance >= TimeUtil.DAY_MILLI_SECONDS){
					long distanceDay = distance / TimeUtil.DAY_MILLI_SECONDS;
					bean.setDistanceTime(distanceDay + (distanceDay > 1 ? " days" :" day"));
				}else if(distance >= TimeUtil.HOUR_MILLI_SECONDS){
					long distanceHour = distance / TimeUtil.HOUR_MILLI_SECONDS;
					bean.setDistanceTime(distanceHour + (distanceHour > 1 ? " hours" :" hour"));
				}
			}
		}
		return list;
	}
	
	public ResultBean toggleMarkup(UserInfo userInfo,String id){
		ResultBean result = new ResultBean();
		if(userInfo == null){
			result.setMessage("please login");
			return result;
		}
		if(StringUtils.isEmpty(id)){
			result.setMessage("please add the paramter id!");
			return result;
		}
		List<TaskInfo> list = taskQuery.queryTaskById(userInfo, id);
		if(list.size() == 0){
			result.setMessage("no such id task!");
			return result;
		}
		TaskInfo task = list.get(0);
		task.setMarkup(!task.isMarkup());
		taskInfoRepository.save(task);
		result.setSuccess(true);
		return result;
	}
	
	public ResultBean cancelTask(UserInfo userInfo,String id){
		ResultBean result = new ResultBean();
		if(userInfo == null){
			result.setMessage("please login");
			return result;
		}
		if(StringUtils.isEmpty(id)){
			result.setMessage("please add the paramter id!");
			return result;
		}
		List<TaskInfo> list = taskQuery.queryTaskById(userInfo, id);
		if(list.size() == 0){
			result.setMessage("no such id task!");
			return result;
		}
		TaskInfo task = list.get(0);
		task.setStatus(TaskStatus.Cancel);
		taskInfoRepository.save(task);
		result.setSuccess(true);
		return result;
	}
	
	public ResultBean loadTask(UserInfo userInfo,String id){
		ResultBean result = new ResultBean();
		if(userInfo == null){
			result.setMessage("please login");
			return result;
		}
		if(StringUtils.isEmpty(id)){
			result.setMessage("please add the paramter id!");
			return result;
		}
		List<TaskInfo> list = taskQuery.queryTaskById(userInfo, id);
		if(list.size() == 0){
			result.setMessage("no such id task!");
			return result;
		}
		TaskInfo task = list.get(0);
		result.setData(task);
		result.setSuccess(true);
		return result;
	}

	//add by zj 17/01/09
	public ResultBean statusFilter(String status){
		List<TaskInfo> taskInfos = taskQuery.loadTaskByStatus(status);
		return ResultBean.getSuccessResult(taskInfos);
	}

	public void finishTask(String id){
		taskQuery.updateTaskToDone(id);
	}

	//add by zj 17/01/13
	public void addActivityInfo(String userId, TaskInfo taskInfo){
		BoardInfo boardInfo = taskQuery.findBoardInfoByUserId(userId);
		String boardId = boardInfo.getId();
		ActivityInfo activityInfo = new ActivityInfo();
		activityInfo.setBoardId(boardId);
		activityInfo.setEventUser(userId);
		activityInfo.setEventDate(new Date());
		activityInfo.setEventType("task");
		String eventMsg = "{\"title\":\""+taskInfo.getTitle()+"\",\"description\":\""+taskInfo.getDescription()+"\"}";
		activityInfo.setEventMsg(eventMsg);
		activityInfoRepository.save(activityInfo);
	}

}
