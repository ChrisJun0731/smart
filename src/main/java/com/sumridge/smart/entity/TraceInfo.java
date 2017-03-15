package com.sumridge.smart.entity;

import java.util.Date;

/**
 * Created by liu on 16/8/4.
 */
public class TraceInfo {

    private String userId;

    private String userName;

    private String opt;

    private Date optDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public Date getOptDate() {
        return optDate;
    }

    public void setOptDate(Date optDate) {
        this.optDate = optDate;
    }
}
