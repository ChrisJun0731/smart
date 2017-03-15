package com.sumridge.smart.controller;

import com.sumridge.smart.bean.PostMessageBean;
import com.sumridge.smart.bean.ResultBean;
import com.sumridge.smart.domain.CurrentUser;
import com.sumridge.smart.service.BoardService;
import com.sumridge.smart.service.UserInfoService;
import com.sun.corba.se.spi.orbutil.fsm.Guard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liu on 16/4/8.
 */
@RestController
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/top")
    public ResultBean getHomeTopBoard(Authentication authentication) {
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if(user != null) {
            ResultBean rs = boardService.getTopBoards(userInfoService.findUserInfo(user.getUserInfo().getEmail()));
            return rs;
        } else {
            return new ResultBean();
        }
    }
    @RequestMapping(value = "/home")
    public ResultBean getHomeMessage(Authentication authentication) {
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if(user != null) {
            ResultBean rs = boardService.getHomeMessage(user.getUserInfo());
            return rs;
        } else {
            return new ResultBean();
        }
    }

    @RequestMapping(value = "/info")
    public ResultBean getBoard(Authentication authentication,@RequestParam(value = "id", required = false) String boardId,@RequestParam(value = "ownerId", required = false) String ownerId) {


        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if(user != null) {
            ResultBean rs = boardService.getBoardInfo(userInfoService.findUserInfo(user.getUserInfo().getEmail()), boardId, ownerId);
            return rs;
        } else {
            return new ResultBean();
        }
    }

    @RequestMapping(value = "/account-info")
    public ResultBean getAccountBoard(Authentication authentication,@RequestParam("id") String ownerId) {


        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if(user != null) {
            ResultBean rs = boardService.getAccountBoardInfo(user.getUserInfo(), ownerId);
            return rs;
        } else {
            return new ResultBean();
        }
    }

    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public ResultBean postMessage(Authentication authentication, @RequestBody PostMessageBean message) {
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if(user != null) {
            ResultBean rs = boardService.postMessage(user.getUserInfo(), message);
            return rs;
        } else {
            return new ResultBean();
        }
    }

    @RequestMapping(value = "/follow")
    public ResultBean followBoard(Authentication authentication,@RequestParam("id") String boardId) {
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if(user != null) {
            ResultBean rs = boardService.followBoard(user.getUserInfo(), boardId);
            return rs;
        } else {
            return new ResultBean();
        }
    }

    @RequestMapping(value = "/unfollow")
    public ResultBean unfollowBoard(Authentication authentication,@RequestParam("id") String boardId) {
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if(user != null) {
            ResultBean rs = boardService.unfollowBoard(user.getUserInfo(), boardId);
            return rs;
        } else {
            return new ResultBean();
        }
    }

    @RequestMapping(value = "/activity")
    public ResultBean getActivity(Authentication authentication,@RequestParam("id") String boardId) {
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if(user != null) {
            ResultBean rs = boardService.getActivity(user.getUserInfo(), boardId);
            return rs;
        } else {
            return new ResultBean();
        }
    }


    @RequestMapping(value = "/topfiles")
    public ResultBean getBoardTopFile(Authentication authentication,@RequestParam("id") String boardId) {
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if(user != null) {
            ResultBean rs = boardService.topfiles(user.getUserInfo(), boardId);
            return rs;
        } else {
            return new ResultBean();
        }
    }

    //add by zj 2016/11/22
    @RequestMapping(value="/removeMsg")
    public void removeMsg(Authentication authentication, @RequestParam("id") String activityId){
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if(user != null){
            boardService.removeMsg(activityId);
        }
    }

    @RequestMapping(value="/comment")
    public void comment(Authentication authentication, @RequestBody String comment, @RequestParam("id") String id){
        CurrentUser user = (CurrentUser)authentication.getPrincipal();
        if(user != null){
            boardService.comment(user.getUserInfo(),comment,id);
        }
    }



}
