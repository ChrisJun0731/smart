package com.sumridge.smart.entity;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by liu on 16/4/26.
 */
public class Contact {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String role;
    private String title;
    private String products;
    private String phone;
    private String email;
    private String userId;
    private String homeAddress;
    private String account;
    private String creator;
    private Date createDate;
    private String owner;
    private String status;
    private String initial;
    private Set<TagInfo> tags;
    private Set<AccountMapping> salesMap;

    //add by zj 16/10/17
    private List<Visible> visibleList;

    //add by zj 16/11/09 用来保存定制的域和值
    private List<Map<String,Object>> customFields;

    public List<Map<String, Object>> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(List<Map<String, Object>> customFields) {
        this.customFields = customFields;
    }

    public List<Visible> getVisibleList() {
        return visibleList;
    }

    public void setVisibleList(List<Visible> visibleList) {
        this.visibleList = visibleList;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Set<AccountMapping> getSalesMap() {
        return salesMap;
    }

    public void setSalesMap(Set<AccountMapping> salesMap) {
        this.salesMap = salesMap;
    }

    public Set<TagInfo> getTags() {
        return tags;
    }

    public void setTags(Set<TagInfo> tags) {
        this.tags = tags;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }
}
