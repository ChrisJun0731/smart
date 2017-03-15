package com.sumridge.smart.test;

import com.alibaba.fastjson.JSON;
import com.sumridge.smart.Application;
import com.sumridge.smart.bean.ContactAggBean;
import com.sumridge.smart.bean.ResultBean;
import com.sumridge.smart.entity.*;

import com.sumridge.smart.query.AccountQuery;
import com.sumridge.smart.query.ContactQuery;
import com.sumridge.smart.query.TaskQuery;

import com.sumridge.smart.service.AccountService;
import com.sumridge.smart.service.ContactService;
import com.sumridge.smart.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 * Created by liu on 16/4/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TestQueryTest {
   @Autowired
   private AccountService accountService;

    @Autowired
    private AccountQuery accountQuery;

    @Autowired
    private TaskQuery taskQuery;

    @Autowired
    private ContactQuery contactQuery;

    @Test
    public void testQuery() {

        ResultBean bean = accountService.getAccountTradeList(null,"2016-09-11", "2016-09-15");
        System.out.println("test-trade");
        System.out.println(JSON.toJSONString(bean));

    }

    @Test
    public void testGetAccountNames(){
//        ResultBean bean = accountService.getAccountNames();
//        System.out.println("test-getAccontNames");
//        System.out.println(JSON.toJSONString(bean));
    }

    @Test
    public void testAccountNames(){
//        AccountInfo list = accountQuery.getAccountNames();
//        System.out.println(JSON.toJSONString(list));
    }
//    @Test
//    public void testPortfolioNames(){
//        ResultBean rs = accountService.getPortfolioTitles();
//        System.out.println(JSON.toJSONString(rs));
//    }

//    @Test
//    public void testContactNames(){
//        ResultBean rs = contactService.getContactNames();
//        System.out.println(JSON.toJSONString(rs));
//    }

    @Test
    public void testTaskFilter(){
        List<TaskInfo> taskInfos = taskQuery.loadTaskByStatus("done");
        System.out.println(JSON.toJSONString(taskInfos));
    }

    @Test
    public void testFinishTask(){
        taskQuery.updateTaskToDone("5760f105d4c6ec6440c5b38e");
    }

    @Test
    public void testGetPortfoliosTitles(){
//        List<String> names = new ArrayList<>();
//        names.add("tech team");
//        List<PortfolioInfo> portfolioInfos = accountQuery.getPortfoliosTitles(names);
//        System.out.println(JSON.toJSONString(portfolioInfos));
    }

    @Test
    public void testgetPortfolioTitles(){
//        ResultBean rs = new ResultBean();
//        List<String> names = new ArrayList<>();
//        names.add("tech team");
//        rs = accountService.getPortfolioTitles(names);
//        System.out.println(JSON.toJSONString(rs));
    }

    @Test
    public void testFindAccountByAccountId(){
        AccountInfo accountInfo = accountQuery.findAccountByAccountId("57302848d4c6025e3e44b1be");
        System.out.println(JSON.toJSONString(accountInfo));
    }

    @Test
    public void testloadMatchInfo(){
        List<String> portfolioTitles = new ArrayList<>();
        portfolioTitles.add("sdf");
        portfolioTitles.add("test5s");
        ResultBean rs = accountService.loadMatchInfo(portfolioTitles);
        System.out.println(JSON.toJSONString(rs));
    }

    @Test
    public void testGetInitials(){
        List<Map> list = contactQuery.getInitials();
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void testGetInitialContacts(){
        UserInfo userInfo = new UserInfo();
        userInfo.setId("57070fded4c66f259e6be211");
        Set<String> set = new HashSet<String>();
        set.add("56fc827ad4c666577c010e0d");
        set.add("5746a12fd4c649fc303fb5c2");
        userInfo.setTeamIdSet(set);
        List<ContactAggBean> list = contactQuery.getInitialContacts("K", userInfo, 5);
        System.out.println(JSON.toJSONString(list));
    }

}
