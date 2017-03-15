package com.sumridge.smart.entity;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by liu on 16/4/7.
 */
public class ActivityInfo {
    @Id
    private String id;
    private String eventType;
    private Date eventDate;
    private String eventUser;
    private String eventMsg;
    private String boardId;
    private List<CommentInfo> commentInfoList;
    private int commentCount;
    private Set<String> pinList;
    private Set<String> likeList;
    private ShareInfo shareInfo;
    private String level;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventMsg() {
        return eventMsg;
    }

    public void setEventMsg(String eventMsg) {
        this.eventMsg = eventMsg;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public List<CommentInfo> getCommentInfoList() {
        return commentInfoList;
    }

    public void setCommentInfoList(List<CommentInfo> commentInfoList) {
        this.commentInfoList = commentInfoList;
    }

    public Set<String> getPinList() {
        return pinList;
    }

    public void setPinList(Set<String> pinList) {
        this.pinList = pinList;
    }

    public Set<String> getLikeList() {
        return likeList;
    }

    public void setLikeList(Set<String> likeList) {
        this.likeList = likeList;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }


    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventUser() {
        return eventUser;
    }

    public void setEventUser(String eventUser) {
        this.eventUser = eventUser;
    }
}
