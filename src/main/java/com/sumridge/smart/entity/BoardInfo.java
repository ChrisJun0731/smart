package com.sumridge.smart.entity;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by liu on 16/4/6.
 */
public class BoardInfo {
    @Id
    private String id;
    //board
    private Set<String> followings;

    //count of following
    private long followingCount;

    //member
    private Set<String> followers;

    //count of follower
    private long followerCount;

    //team or user
    private String ownerId;

    private Date createDate;

    private String type;

    private List<Visible> visibles;

    private String boardName;

    private String description;

    private String boardImg;

    private Date updateDate;

    //doc
    private long documentCount;

    private Set<String> editors;

    public Set<String> getEditors() {
        return editors;
    }

    public void setEditors(Set<String> editors) {
        this.editors = editors;
    }

    public long getDocumentCount() {
        return documentCount;
    }

    public void setDocumentCount(long documentCount) {
        this.documentCount = documentCount;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBoardImg() {
        return boardImg;
    }

    public void setBoardImg(String boardImg) {
        this.boardImg = boardImg;
    }

    public List<Visible> getVisibles() {
        return visibles;
    }

    public void setVisibles(List<Visible> visibles) {
        this.visibles = visibles;
    }

    public long getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(long followingCount) {
        this.followingCount = followingCount;
    }

    public long getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(long followerCount) {
        this.followerCount = followerCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Set<String> getFollowings() {
        return followings;
    }

    public void setFollowings(Set<String> followings) {
        this.followings = followings;
    }

    public Set<String> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<String> followers) {
        this.followers = followers;
    }
}
