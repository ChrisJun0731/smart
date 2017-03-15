package com.sumridge.smart.bean;

/**
 * Created by liu on 16/3/29.
 */
public class ResultBean {
    private boolean success;

    private String message;

    private Object data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ResultBean getSuccessResult(Object data){
        ResultBean rs = new ResultBean();
        rs.setSuccess(true);
        rs.setData(data);
        return rs;
    }

    public static ResultBean getSuccessResult(){

        return getSuccessResult(null);
    }

    public static ResultBean geFailResult(Object data, String message){
        ResultBean rs = new ResultBean();
        rs.setSuccess(false);
        rs.setData(data);
        rs.setMessage(message);
        return rs;
    }
}
