package com.sumridge.smart.test;

import com.sumridge.smart.Application;
import com.sumridge.smart.bean.ResultBean;
import com.sumridge.smart.entity.*;
import com.sumridge.smart.service.BoardService;
import com.sumridge.smart.service.CompanyService;
import com.sumridge.smart.service.TeamInfoService;
import com.sumridge.smart.service.UserInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by liu on 16/3/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TestDataInitTest {
//    @Autowired
//    TeamInfoService teamInfoService;
//
//    @Autowired
//    UserInfoService userInfoService;
//
//    @Autowired
//    BoardService boardService;
//    @Autowired
//    CompanyService companyService;
//
//
//    @Test
//    public void testInsertTeamInfo() {
//
//        TeamInfo info = new TeamInfo();
//        info.setLevel("1");
//        info.setStatus("1");
//        info.setTeamId("1");
//        info.setTeamName("Sumridge");
//        ResultBean rs = teamInfoService.saveInfo(info, null);
//        Assert.assertTrue(rs.isSuccess());
//    }
//
//    @Test
//    public void testInsertUserInfo() {
//        TeamInfo team = teamInfoService.findTeamInfo("1");
//        UserInfo userInfo = new UserInfo();
//        userInfo.setEmail("test@test.com");
//        userInfo.setPassword(new BCryptPasswordEncoder().encode("123@"));
//        userInfo.setRole("USER");
//        userInfo.setUserId("1001");
//        userInfo.setDescription("test desc");
//        userInfo.setEmail("test@test.com");
//        userInfo.setFirstName("test");
//        userInfo.setLastName("last");
//        userInfo.setPhone("555 1234536");
//        userInfo.setPhoto(null);
//        userInfo.setTeamIdSet(Stream.of(team.getId()).collect(Collectors.toSet()));
//        userInfo.setTitle("trader");
//        userInfo.setUserId("1001");
//        userInfo.setCreateDate(new Date());
//        ResultBean rs = userInfoService.saveUserInfo(userInfo);
//        Assert.assertTrue(rs.isSuccess());
//    }
//
//    @Test
//    public void testInsertBoard() {
//        TeamInfo team = teamInfoService.findTeamInfo("1");
//        UserInfo userBean = userInfoService.findUserInfo("test@test.com");
//
//        BoardInfo info = new BoardInfo();
//        info.setOwnerId(team.getId());
//        info.setCreateDate(new Date());
//        //TODO add more test data
//        info.setType("T");
//        Visible vis = new Visible();
//        vis.setVisType("T");
//        vis.setVisId(team.getId());
//        info.setVisibles(Arrays.asList(vis));
//        info.setBoardImg(team.getPhoto());
//        info.setBoardName(team.getTeamName());
//        info.setDescription(team.getDescription());
//        info.setUpdateDate(new Date());
//        boardService.saveBoard(info);
//
//        info = new BoardInfo();
//        info.setCreateDate(new Date());
//        info.setOwnerId(userBean.getId());
//        //TODO add more test data
//        info.setType("H");
//        info.setVisibles(Arrays.asList(vis));
//        info.setBoardImg(userBean.getPhoto());
//        info.setBoardName(userBean.getFirstName()+" "+ userBean.getLastName());
//        info.setDescription(userBean.getDescription());
//        info.setUpdateDate(new Date());
//        boardService.saveBoard(info);
//    }
//
//    @Test
//    public void testInsertCompanyInfo() {
//
//        CompanyInfo info = new CompanyInfo();
//        info.setAddress("test address");
//        info.setCreateDate(new Date());
//        info.setCreator("system");
//        info.setInitial("S");
//        info.setLongName("SumRidge Partners LLC");
//        info.setShortName("Sumridge");
//        List<AccountInfo> accountInfoList = new ArrayList<>();
//        AccountInfo accountInfo = new AccountInfo();
//
//        accountInfo.setCategoryName("test-category");
//        accountInfo.setCreateDate(new Date());
//        accountInfo.setCreator("system");
//        accountInfo.setDescription("test-desc");
//        accountInfo.setInitial("T");
//        accountInfo.setLongName("Test-App");
//        accountInfo.setName("Test");
//        accountInfo.setId(info.getShortName()+"-"+accountInfo.getName());
//        accountInfoList.add(accountInfo);
//
//        accountInfo = new AccountInfo();
//        accountInfo.setCategoryName("test-category");
//        accountInfo.setCreateDate(new Date());
//        accountInfo.setCreator("system");
//        accountInfo.setDescription("test-desc");
//        accountInfo.setInitial("T");
//        accountInfo.setLongName("Trade-App");
//        accountInfo.setName("Trade");
//        accountInfo.setId(info.getShortName()+"-"+accountInfo.getName());
//        accountInfoList.add(accountInfo);
//
//        accountInfo = new AccountInfo();
//        accountInfo.setCategoryName("dev-category");
//        accountInfo.setCreateDate(new Date());
//        accountInfo.setCreator("system");
//        accountInfo.setDescription("dev-desc");
//        accountInfo.setInitial("D");
//        accountInfo.setLongName("Dev-App");
//        accountInfo.setName("Dev");
//        accountInfo.setId(info.getShortName()+"-"+accountInfo.getName());
//        accountInfoList.add(accountInfo);
//        info.setAccountInfos(accountInfoList);
//        companyService.saveCompanyInfo(info);
//    }
@Test
public void testFileType() {

}
}
