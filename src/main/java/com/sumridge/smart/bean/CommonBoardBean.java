package com.sumridge.smart.bean;

import com.sumridge.smart.entity.BoardInfo;

/**
 * Created by liu on 16/4/13.
 */
public class CommonBoardBean extends BoardInfo {
    private boolean isEditor;
    private boolean isFollow;

    public boolean isEditor() {
        return isEditor;
    }

    public void setEditor(boolean editor) {
        isEditor = editor;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }
}
