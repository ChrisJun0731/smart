package com.sumridge.smart.controller;

import com.sumridge.smart.bean.AccountDetailBean;
import com.sumridge.smart.bean.MapBean;
import com.sumridge.smart.bean.ResultBean;
import com.sumridge.smart.domain.CurrentUser;
import com.sumridge.smart.entity.TeamInfo;
import com.sumridge.smart.service.BoardService;
import com.sumridge.smart.service.TeamInfoService;
import com.sumridge.smart.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by liu on 16/5/23.
 */
@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamInfoService teamInfoService;

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private BoardService boardService;

    @RequestMapping(value = "/list")
    public ResultBean getTeamsList(Authentication authentication) {
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if(user != null) {
            ResultBean rs = teamInfoService.getTeamList(userInfoService.findUserInfo(user.getUserInfo().getEmail()));
            return rs;
        } else {
            return new ResultBean();
        }
    }

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public ResultBean saveTeam(Authentication authentication, @RequestBody TeamInfo bean) {
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if (user != null) {
            if(bean.getId() == null) {
               return teamInfoService.createInfo(bean, user.getUserInfo());

            } else {
                return teamInfoService.saveInfo(bean,userInfoService.findUserInfo(user.getUserInfo().getEmail()));
            }

        } else {
            return new ResultBean();
        }

    }

    @RequestMapping(value = "/info")
    public ResultBean getTeam(@RequestParam("id") String teamId) {
        ResultBean rs = teamInfoService.getTeamInfo(teamId);
        return rs;
    }

    @RequestMapping(value = "/names")
    public ResultBean getNameList(Authentication authentication) {
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if (user != null) {
            ResultBean rs = teamInfoService.getTeamNameList(user.getUserInfo());
            return rs;
        } else {
            return new ResultBean();
        }
    }

    @RequestMapping(value = "/board")
    public ResultBean getBoard(@RequestParam("id") String ownerId) {
        ResultBean rs = ResultBean.getSuccessResult(boardService.getBoardIdByOwner(ownerId));
        return rs;
    }

    @RequestMapping(value = "/member/{teamId}", method = RequestMethod.POST)
    public ResultBean saveMember(Authentication authentication, @RequestBody MapBean bean,@PathVariable String teamId) {
        return teamInfoService.saveMember(bean, teamId);
    }

    @RequestMapping(value = "/rmmember/{teamId}", method = RequestMethod.POST)
    public ResultBean deleteMember(Authentication authentication, @RequestBody MapBean bean,@PathVariable String teamId) {
        return teamInfoService.deleteMember(bean, teamId);
    }

}
