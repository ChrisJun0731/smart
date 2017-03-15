package com.sumridge.smart.service;

import com.sumridge.smart.bean.ResultBean;
import com.sumridge.smart.dao.ReportInfoRepository;
import com.sumridge.smart.entity.ReportInfo;
import com.sumridge.smart.entity.TraceInfo;
import com.sumridge.smart.entity.UserInfo;
import com.sumridge.smart.entity.Visible;
import com.sumridge.smart.query.ReportQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by liu on 16/8/4.
 */
@Service
public class ReportService {

    @Autowired
    private ReportInfoRepository reportInfoRepository;

    @Autowired
    private ReportQuery reportQuery;


    public ResultBean saveReport(ReportInfo reportInfo, UserInfo userInfo) {

        ReportInfo dbInfo = reportInfoRepository.findOne(reportInfo.getId());
        if(dbInfo != null) {
            //update dbInfo
            dbInfo.setTitle(reportInfo.getTitle());
            dbInfo.setReportUrl(reportInfo.getReportUrl());
            setTraceOfReport(dbInfo, "update", userInfo);
            //TODO visable check
        } else {
            //create new report
            setTraceOfReport(reportInfo, "create", userInfo);
            setVisibles(reportInfo, userInfo);
            dbInfo = reportInfo;
        }
        reportInfoRepository.save(dbInfo);
        return ResultBean.getSuccessResult();
    }

    private void setVisibles(ReportInfo reportInfo, UserInfo userInfo) {

        List<Visible> visList = new ArrayList<>();
        for(String teamId : userInfo.getTeamIdSet()) {
            Visible vis = new Visible();
            vis.setVisType("T");
            vis.setVisId(teamId);
            visList.add(vis);
        }
        reportInfo.setVisibles(visList);

    }

    private void setTraceOfReport(ReportInfo reportInfo, String opt, UserInfo userInfo) {

        TraceInfo trace = new TraceInfo();
        trace.setOpt(opt);
        trace.setOptDate(new Date());
        trace.setUserId(userInfo.getId());
        trace.setUserName(userInfo.getFirstName()+" " + userInfo.getLastName());

        if(reportInfo.getTraceInfoList() == null) {
            reportInfo.setTraceInfoList(Arrays.asList(trace));
        } else {
            reportInfo.getTraceInfoList().add(trace);
        }
    }

    public ResultBean getReportList(UserInfo userInfo) {

        return ResultBean.getSuccessResult(reportQuery.getSaleList(20, userInfo.getTeamIdSet()));
    }

    public ResultBean removeReport(UserInfo userInfo, String id) {

        return ResultBean.getSuccessResult();
    }


    public ResultBean getReport(UserInfo userInfo, String id) {

        return ResultBean.getSuccessResult(reportInfoRepository.findOne(id));
    }
}
