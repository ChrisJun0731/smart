package com.sumridge.smart.entity;

import java.util.List;

/**
 * Created by liu on 16/8/4.
 */
public class ReportInfo {

    private String id;

    private String title;

    private String reportUrl;

    private List<TraceInfo> traceInfoList;

    private List<Visible> visibles;

    public List<TraceInfo> getTraceInfoList() {
        return traceInfoList;
    }

    public void setTraceInfoList(List<TraceInfo> traceInfoList) {
        this.traceInfoList = traceInfoList;
    }

    public List<Visible> getVisibles() {
        return visibles;
    }

    public void setVisibles(List<Visible> visibles) {
        this.visibles = visibles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }
}
