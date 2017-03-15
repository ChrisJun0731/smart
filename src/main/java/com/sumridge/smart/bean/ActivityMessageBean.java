package com.sumridge.smart.bean;

import com.sumridge.smart.entity.CommentInfo;

import java.util.List;

/**
 * Created by liu on 16/4/12.
 */
public class ActivityMessageBean {
    private String id;
    private String content;
    private String userId;
    private String userName;
    private String userPhoto;
    private String messageType;
    private String boardId;
    private long time;
    private int comments;

    //add by zj 2016/11/22 存放评论的内容
    private List<CommentInfo> commentInfoList;

    public List<CommentInfo> getCommentInfoList() {
        return commentInfoList;
    }

    public void setCommentInfoList(List<CommentInfo> commentInfoList) {
        this.commentInfoList = commentInfoList;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
