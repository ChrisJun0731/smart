package com.sumridge.smart.entity;

import com.sumridge.smart.bean.EmailBean;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.Set;

/**
 * Created by liu on 16/11/2.
 */
public class MatchInfo {
    @Id
    private String id;

    private Set<EmailBean> customer;

    private String title;

    private String content;

    private Set<MatchDetail> salesMap;

    private String creator;

    private Date createDate;

    private boolean isSent;

    private String status;

    //TODO add reply status and collection data,link trade data and ss on
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<EmailBean> getCustomer() {
        return customer;
    }

    public void setCustomer(Set<EmailBean> customer) {
        this.customer = customer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<MatchDetail> getSalesMap() {
        return salesMap;
    }

    public void setSalesMap(Set<MatchDetail> salesMap) {
        this.salesMap = salesMap;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
