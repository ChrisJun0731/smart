package com.sumridge.smart.controller;

import com.sumridge.smart.bean.ResultBean;
import com.sumridge.smart.bean.TeamUserInfo;
import com.sumridge.smart.bean.TokenBean;
import com.sumridge.smart.domain.CurrentUser;
import com.sumridge.smart.entity.UserInfo;
import com.sumridge.smart.service.BoardService;
import com.sumridge.smart.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by liu on 16/4/5.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private BoardService boardService;

    @RequestMapping(value = "/info")
    public ResultBean getBaseInfo(Authentication authentication) {
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if(user != null) {

            ResultBean rs = new ResultBean();
            rs.setData(userInfoService.findUserInfo(user.getUserInfo().getEmail()));
            rs.setSuccess(true);
            return rs;
        } else {
            return new ResultBean();
        }
    }

    @RequestMapping(value = "/home")
    public ResultBean getHomeBoard(Authentication authentication) {
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if(user != null) {
            ResultBean rs = boardService.getUserBoard(userInfoService.findUserInfo(user.getUserInfo().getEmail()));
            return rs;
        } else {
            return new ResultBean();
        }
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public ResultBean saveHomeUserProfile(Authentication authentication,@RequestBody UserInfo userInfo) {
        //TODO check user
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if(user != null) {
            return userInfoService.saveUserInfo(userInfo);
        } else {
            return new ResultBean();
        }

    }

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public ResultBean saveInfo(Authentication authentication,@RequestBody TeamUserInfo userInfo) {
        //TODO check user
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if(user != null) {
            return userInfoService.saveNewUserInfo(userInfo);
        } else {
            return new ResultBean();
        }

    }

    @RequestMapping(value = "/list")
    public ResultBean getTeamsUser(Authentication authentication) {
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if(user != null) {
            ResultBean rs = userInfoService.getUserList(userInfoService.findUserInfo(user.getUserInfo().getEmail()));
            return rs;
        } else {
            return new ResultBean();
        }
    }

    @RequestMapping(value = "/chat-token")
    public TokenBean getToken(Authentication authentication,HttpServletResponse response) {
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        response.setHeader("Access-Control-Allow-Origin","http://localhost:3000");
        response.setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
        response.setHeader("Access-Control-Allow-Credentials","true");

        return userInfoService.findUserChatToken(user.getUserInfo());
    }

    //add by zj
    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public ResultBean uploadFile(Authentication authentication, @RequestParam("file") MultipartFile file){
        LOGGER.info("file:"+file.getName()+"/"+file.getContentType()+"/"+file.getOriginalFilename()+"/"+file.getSize());
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if (user != null) {
            ResultBean rs = userInfoService.saveFile(user.getUserInfo(), file);
            return rs;
        } else {
            return new ResultBean();
        }
    }


}
