package com.sumridge.smart.service;

import com.mongodb.gridfs.GridFSDBFile;
import com.sumridge.smart.bean.AddFolderBean;
import com.sumridge.smart.bean.DiskFileOptBean;
import com.sumridge.smart.bean.FolderPathBean;
import com.sumridge.smart.bean.ResultBean;
import com.sumridge.smart.dao.BoardFileInfoRepository;
import com.sumridge.smart.entity.BoardFileInfo;
import com.sumridge.smart.entity.FileViewer;
import com.sumridge.smart.entity.UserInfo;
import com.sumridge.smart.query.BoardFileQuery;
import com.sumridge.smart.query.UserQuery;
import com.sumridge.smart.util.FileTypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liu on 16/4/15.
 */
@Service
public class DiskService {
    @Autowired
    private BoardFileQuery boardFileQuery;
    @Autowired
    private UserQuery userQuery;
    @Autowired
    private GridFileService gridFileService;
    @Autowired
    private BoardFileInfoRepository boardFileInfoRepository;

    public ResultBean getDiskList(UserInfo userInfo, String boardId, String root) {

        //TODO ishome check
        List<BoardFileInfo> fileList = boardFileQuery.queryFileList(boardId, root, true);
        fileList.forEach(boardFileInfo -> {
            if(boardFileInfo.getCreator().equals(userInfo.getId())) {
                boardFileInfo.setCreator(userInfo.getFirstName()+" "+userInfo.getLastName());
            } else {
                UserInfo user = userQuery.getBaseUserInfo(boardFileInfo.getCreator());
                boardFileInfo.setCreator(user.getFirstName()+" "+user.getLastName());
            }
        });
        ResultBean resultBean = new ResultBean();
        resultBean.setSuccess(true);
        resultBean.setData(fileList);
        return resultBean;
    }

    public ResultBean checkDuplicate(UserInfo userInfo, String boardId, String root, String folderName) {
        //check duplicate
        boolean check = boardFileQuery.exsitFile(boardId, root, folderName);
        ResultBean resultBean = new ResultBean();
        resultBean.setSuccess(check);
        return resultBean;
    }

    public ResultBean addFolder(UserInfo userInfo, AddFolderBean folderBean) {


        //add folder
        boolean check = boardFileQuery.exsitFile(folderBean.getBoardId(), folderBean.getRoot(), folderBean.getFoldName());
        ResultBean resultBean = new ResultBean();
        if (!check) {
            boardFileQuery.addFolder(folderBean.getBoardId(), folderBean.getRoot(), folderBean.getFoldName(), userInfo.getId(), folderBean.getStatus());
            resultBean.setSuccess(true);
        } else {
            resultBean.setSuccess(check);
        }

        return resultBean;
    }

    public ResultBean getPath(UserInfo userInfo, String boardId, String root) {
        List<BoardFileInfo> list = boardFileQuery.queryPathInfo(boardId, root);
        List<FolderPathBean> resultList = new ArrayList<>();
        FolderPathBean home = new FolderPathBean();
        home.setPath("home");
        resultList.add(home);
        for (int i = list.size()-1;i>=0;i--){
            BoardFileInfo boardFileInfo = list.get(i);
            FolderPathBean bean = new FolderPathBean();
            bean.setPath(boardFileInfo.getFileName());
            bean.setRoot(boardFileInfo.getId());
            resultList.add(bean);
        }
        ResultBean resultBean = new ResultBean();
        resultBean.setSuccess(true);
        resultBean.setData(resultList);
        return resultBean;
    }

    public ResultBean saveFile(UserInfo userInfo, String boardId, String root, String status, MultipartFile file) {

        try {
            Object fileId = gridFileService.saveFile(file);
            FileViewer viewer = FileTypeUtil.getFileType(file.getContentType(),file.getOriginalFilename());
            BoardFileInfo info = new BoardFileInfo();
            info.setBoardId(boardId);
            info.setCreateDate(new Date());
            info.setCreator(userInfo.getId());
            info.setFileId(fileId.toString());
            info.setFileName(file.getOriginalFilename());
            info.setFileSize(file.getSize());
            info.setFileType(viewer.getFormat());
            info.setFileViewer(viewer);
            info.setStatus(status);
            info.setUpdateDate(info.getCreateDate());
            boardFileQuery.setPathSet(root, info);
            boardFileInfoRepository.save(info);
            ResultBean resultBean = new ResultBean();
            resultBean.setSuccess(true);
            return resultBean;
        } catch (IOException e) {
            e.printStackTrace();
            return new ResultBean();
        }



    }

    public ResultBean removeFiles(UserInfo userInfo, List<DiskFileOptBean> removeList) {

        boardFileQuery.logicRemove(removeList);

        ResultBean resultBean = new ResultBean();
        resultBean.setSuccess(true);
        return resultBean;

    }

    public void downloadFile(HttpServletResponse response, UserInfo userInfo, String fileId) {
        BoardFileInfo info = boardFileQuery.getFileInfo(fileId);
        GridFSDBFile file = gridFileService.getFile(info.getFileId());
        response.setContentType(file.getContentType());
        response.addHeader("Content-Disposition","attachment; filename=" + file.getFilename());
        try {
            file.writeTo(response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        response.setHeader();
    }
}
