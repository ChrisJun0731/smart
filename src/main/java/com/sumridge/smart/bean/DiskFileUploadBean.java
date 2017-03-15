package com.sumridge.smart.bean;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by liu on 16/4/19.
 */
public class DiskFileUploadBean {

    private String boardId;
    private String root;
    private MultipartFile file;

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
