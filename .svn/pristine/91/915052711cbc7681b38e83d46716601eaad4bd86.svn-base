package com.sumridge.smart.controller;

import com.sumridge.smart.bean.AddFolderBean;
import com.sumridge.smart.bean.DiskFileOptBean;
import com.sumridge.smart.bean.ResultBean;
import com.sumridge.smart.domain.CurrentUser;
import com.sumridge.smart.service.DiskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by liu on 16/4/15.
 */
@RestController
@RequestMapping("/disk")
public class DiskController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiskController.class);
    @Autowired
    private DiskService diskService;

    @RequestMapping(value = "/list")
    public ResultBean getBoard(Authentication authentication, @RequestParam("id") String boardId, @RequestParam(value="root",required=false) String root) {


        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if (user != null) {
            ResultBean rs = diskService.getDiskList(user.getUserInfo(), boardId, root);
            return rs;
        } else {
            return new ResultBean();
        }
    }
    @RequestMapping(value = "/folder", method = RequestMethod.POST)
    public ResultBean addFolder(Authentication authentication,@RequestBody AddFolderBean folderBean) {
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if (user != null) {
            ResultBean rs = diskService.addFolder(user.getUserInfo(), folderBean);
            return rs;
        } else {
            return new ResultBean();
        }
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public ResultBean removeBatch(Authentication authentication,@RequestBody List<DiskFileOptBean> removeList) {

        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if (user != null) {
            ResultBean rs = diskService.removeFiles(user.getUserInfo(), removeList);
            return rs;
        } else {
            return new ResultBean();
        }
    }

    @RequestMapping(value = "/download")
    public void downloadFile(HttpServletResponse response, Authentication authentication, @RequestParam("id") String fileId){


        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if (user != null) {
            diskService.downloadFile(response, user.getUserInfo(), fileId);
        } else {
            return;
        }
    }

    @RequestMapping(value = "/path")
    public ResultBean getPath(Authentication authentication, @RequestParam("id") String boardId, @RequestParam(value="root",required=false) String root) {


        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if (user != null) {
            ResultBean rs = diskService.getPath(user.getUserInfo(), boardId, root);
            return rs;
        } else {
            return new ResultBean();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public ResultBean uploadFile(Authentication authentication,@RequestParam("status") String status, @RequestParam("id") String boardId, @RequestParam(value="root",required=false) String root, @RequestParam("file") MultipartFile file) {
        LOGGER.info("boardId:"+boardId+"/root:"+root+"/stauts:"+status);
        LOGGER.info("file:"+file.getName()+"/"+file.getContentType()+"/"+file.getOriginalFilename()+"/"+file.getSize());
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if (user != null) {
            ResultBean rs = diskService.saveFile(user.getUserInfo(), boardId, root, status, file);
            return rs;
        } else {
            return new ResultBean();
        }
    }



}
