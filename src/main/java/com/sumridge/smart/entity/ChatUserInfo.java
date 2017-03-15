package com.sumridge.smart.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by liu on 16/6/23.
 */
public class ChatUserInfo {


    @Field("_id")
    private String id;

    private Date createdAt;

    private Map<String,Object> services;

    private List<Map<String, Object>> emails;

    private String name;

    private String username;

    private boolean active;

    private String statusDefault;

    private List<String> roles;

    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Map<String, Object> getServices() {
        return services;
    }

    public void setServices(Map<String, Object> services) {
        this.services = services;
    }

    public List<Map<String, Object>> getEmails() {
        return emails;
    }

    public void setEmails(List<Map<String, Object>> emails) {
        this.emails = emails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getStatusDefault() {
        return statusDefault;
    }

    public void setStatusDefault(String statusDefault) {
        this.statusDefault = statusDefault;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
