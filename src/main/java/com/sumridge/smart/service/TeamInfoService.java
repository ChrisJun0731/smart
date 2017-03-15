package com.sumridge.smart.service;

import com.sumridge.smart.bean.MapBean;
import com.sumridge.smart.bean.ResultBean;
import com.sumridge.smart.bean.TeamNameBean;
import com.sumridge.smart.dao.TeamInfoRepository;
import com.sumridge.smart.dao.UserInfoRepository;
import com.sumridge.smart.entity.TeamInfo;
import com.sumridge.smart.entity.UserInfo;
import com.sumridge.smart.query.BoardQuery;
import com.sumridge.smart.query.TeamQuery;
import com.sumridge.smart.query.UserQuery;
import com.sumridge.smart.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by liu on 16/3/29.
 */
@Service
public class TeamInfoService {
    @Autowired
    private TeamInfoRepository teamInfoRepository;
    @Autowired
    private TeamQuery teamQuery;
    @Autowired
    private BoardService boardService;
    @Autowired
    private UserQuery userQuery;
    @Autowired
    private BoardQuery boardQuery;


    public ResultBean saveInfo(TeamInfo info, UserInfo userInfo) {
        info.setInitial(StringUtil.getInitChar(info.getTeamName()));
        teamQuery.updateTeamInfo(info);

        //refresh teamInfo board
        boardService.saveTeamBoard(info);

        ResultBean rs = new ResultBean();
        rs.setSuccess(true);
        return rs;
    }

    public ResultBean createInfo(TeamInfo info, UserInfo userInfo) {
        info.setCreateTime(new Date());
        info.setCreator(userInfo.getId());
        MapBean bean = new MapBean();
        bean.setText(userInfo.getEmail());
        bean.setRole("1");
        bean.setId(userInfo.getId());
        info.setUserList(Stream.of(bean).collect(Collectors.toSet()));
        info.setInitial(StringUtil.getInitChar(info.getTeamName()));
        teamInfoRepository.save(info);

        //add teamid to user info
        userQuery.addTeamSetId(info.getId(), userInfo.getId());

        //refresh teamInfo board
        boardService.saveTeamBoard(info);
        return ResultBean.getSuccessResult();
    }

//    private void addTeamSet(String id, Set<MapBean> userList) {
//
//        Set<String> emailSet =
//    }

    public TeamInfo findTeamInfo(String teamId) {
        return teamInfoRepository.findByTeamId(teamId);
    }

    public ResultBean getTeamList(UserInfo userInfo) {

        return ResultBean.getSuccessResult(teamQuery.queryList(userInfo.getTeamIdSet()));
    }

    public ResultBean getTeamInfo(String teamId) {
        return ResultBean.getSuccessResult(teamInfoRepository.findOne(teamId));
    }

    public ResultBean saveMember(MapBean bean, String teamId) {
        if(bean.getId() == null) {
            //push member
            pushMember(bean, teamId);
        } else {

            MapBean member = teamQuery.getMember(bean.getId(), teamId);

            if(member.getText().equals(bean.getText())) {
                updateMember(bean, teamId, member);

            } else {
                removeMember(teamId, member);
                pushMember(bean, teamId);
            }

        }
        return ResultBean.getSuccessResult(bean);
    }

    private void updateMember(MapBean bean, String teamId, MapBean member) {
        if(!member.getRole().equals(bean.getRole())) {

            teamQuery.saveMember(bean, teamId);
            if(bean.getRole().equals("0")) {
                boardQuery.removeEditor(bean.getId(), teamId);
            } else {
                boardQuery.addEditor(bean.getId(), teamId);
            }
        }
    }

    private void removeMember(String teamId, MapBean member) {
        teamQuery.removeMember(member, teamId);
        if(member.getRole().equals("1")) {
            boardQuery.removeEditor(member.getId(), teamId);
        }
        userQuery.removeTeamId(member.getId(), teamId);
    }

    private void pushMember(MapBean bean, String teamId) {
        UserInfo user = userQuery.getUserInfoByEmail(bean.getText());
        bean.setId(user.getId());

        teamQuery.pushMember(bean, teamId);

        if(bean.getRole().equals("1")) {
            //add editor of board;
            boardQuery.addEditor(user.getId(), teamId);
        }

        //add to user
        userQuery.addTeamSetId(teamId, bean.getId());
    }


    public ResultBean deleteMember(MapBean bean, String teamId) {
        removeMember(teamId, bean);
        return ResultBean.getSuccessResult();
    }

    public ResultBean getTeamNameList(UserInfo userInfo) {
        List<TeamNameBean> list = teamQuery.queryNameList();
        return ResultBean.getSuccessResult(list);
    }

}
