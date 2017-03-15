package com.sumridge.smart.entity;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by liu on 16/4/26.
 */
public class CompanyInfo {
    @Id
    private String id;

    private String shortName;
    private String longName;
    private Date openDate;
    private Date closeDate;
    private String creator;
    private Date createDate;
    private String address;

    private String email;
    private String phone;
    private String city;
    private String zipCode;
    private String state;
    private String country;
    private Set<TagInfo> tags;
    private String initial;
    private List<AccountInfo> accountInfos;
    private String description;
    //add 2016-9-9
    private String uid;
    private Date updateTms;
    private String address2;
    private String acctType;
    private String status;
    private Date firstTradeDate;
    private Date lastTradeDate;
    private String acctOpenUid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getUpdateTms() {
        return updateTms;
    }

    public void setUpdateTms(Date updateTms) {
        this.updateTms = updateTms;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getFirstTradeDate() {
        return firstTradeDate;
    }

    public void setFirstTradeDate(Date firstTradeDate) {
        this.firstTradeDate = firstTradeDate;
    }

    public Date getLastTradeDate() {
        return lastTradeDate;
    }

    public void setLastTradeDate(Date lastTradeDate) {
        this.lastTradeDate = lastTradeDate;
    }

    public String getAcctOpenUid() {
        return acctOpenUid;
    }

    public void setAcctOpenUid(String acctOpenUid) {
        this.acctOpenUid = acctOpenUid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Set<TagInfo> getTags() {
        return tags;
    }

    public void setTags(Set<TagInfo> tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AccountInfo> getAccountInfos() {
        return accountInfos;
    }

    public void setAccountInfos(List<AccountInfo> accountInfos) {
        this.accountInfos = accountInfos;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
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
}
