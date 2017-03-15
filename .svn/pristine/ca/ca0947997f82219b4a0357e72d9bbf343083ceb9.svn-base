package com.sumridge.smart.controller;

import com.sumridge.smart.bean.ResultBean;
import com.sumridge.smart.domain.CurrentUser;
import com.sumridge.smart.entity.AppointmentInfo;
import com.sumridge.smart.entity.ReportInfo;
import com.sumridge.smart.entity.UserInfo;
import com.sumridge.smart.service.AppointmentService;
import com.sumridge.smart.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {
	
	@Autowired
	public ReportService reportService;
	
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public ResultBean getReportList(Authentication authentication){
		CurrentUser user = (CurrentUser) authentication.getPrincipal();
		ResultBean bean = new ResultBean();
		if(user != null && user.getUserInfo() != null){
			return reportService.getReportList(user.getUserInfo());

		}
		return bean;
	}
	
	@RequestMapping(value="/info",method = RequestMethod.POST)
	public ResultBean saveReport(Authentication authentication,@RequestBody ReportInfo reportInfo){
		CurrentUser user = (CurrentUser) authentication.getPrincipal();

		if(user != null && user.getUserInfo() != null){
			UserInfo userInfo = user.getUserInfo();

			return reportService.saveReport(reportInfo, userInfo);
		} else {
            ResultBean bean = new ResultBean();
            return bean;
        }


	}

    @RequestMapping(value="/info/{id}",method = RequestMethod.DELETE)
    public ResultBean removeReport(Authentication authentication, @PathVariable("id") String id) {
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        ResultBean bean = new ResultBean();
        if (user != null && user.getUserInfo() != null) {
            return reportService.removeReport(user.getUserInfo(), id);
        }
        return bean;
    }

    @RequestMapping(value="/info",method = RequestMethod.GET)
    public ResultBean getReport(Authentication authentication, @RequestParam("id") String id) {
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        ResultBean bean = new ResultBean();
        if (user != null && user.getUserInfo() != null) {
            return reportService.getReport(user.getUserInfo(), id);
        }
        return bean;
    }
	

}
