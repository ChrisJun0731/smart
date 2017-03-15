package com.sumridge.smart.bean;

/**
 * Created by liu on 16/4/18.
 */
public class AddFolderBean {

    private String boardId;

    private String foldName;

    private String root;

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getFoldName() {
        return foldName;
    }

    public void setFoldName(String foldName) {
        this.foldName = foldName;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }
}
