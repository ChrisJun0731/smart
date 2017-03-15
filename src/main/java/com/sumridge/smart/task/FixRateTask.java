package com.sumridge.smart.task;

import com.sumridge.smart.entity.TaskInfo;
import com.sumridge.smart.query.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by liu on 17/3/13.
 */
@Component
public class FixRateTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(FixRateTask.class);

    @Autowired
    private TaskQuery taskQuery;

    @Scheduled(fixedRate = 60000)
    public void reportCurrentTime() {
        LOGGER.info("check task from db");

        //1、获取当前系统时间的信息 月、日、时、分、秒
        Calendar calendar = Calendar.getInstance();


        //2、从数据库中查询出status为processing的task
        List<TaskInfo> taskInfoList = taskQuery.queryProcessingTask();

        //3、获取task的设定的闹铃时间，并和当前系统时间进行比较
        for(TaskInfo taskInfo: taskInfoList){
            if(isEqual(calendar, taskInfo.getAlertTime())){
                //4、如果符合闹铃时间吻合，那么再看task的repeatType的类型。
                int type = taskInfo.getRepeatType();
                switch(type){
                    //never repeat
                    case 0: break;
                    //only once
//                    case 1: 执行task 将task的状态置为done break;
                    //every day
//                    case 2: 执行task;
                    //workday
//                    case 3: 先判断是否是工作日 执行task;
                    //every week
//                    case 4: 先查询出设置的是周几闹铃 执行task;
                    //every month
//                    case 5: 先查询设置的日期 执行task;
                    //every year
//                    case 6: 先查询设置的月日 执行task;
                }
            }
        }


        //6、如何符合触发的条件那么执行此任务。否则不执行。

    }

    public Boolean isEqual(Calendar currentDate, Date taskDate){
        int currentHour = currentDate.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentDate.get(Calendar.MINUTE);
        Calendar taskCalendar = Calendar.getInstance();
        taskCalendar.setTime(taskDate);
        int taskHour = taskCalendar.get(Calendar.HOUR_OF_DAY);
        int taskMinute = taskCalendar.get(Calendar.MINUTE);
        if(currentHour == taskHour && currentMinute == taskMinute){
            return true;
        }
        else
        {
            return false;
        }
    }
}
