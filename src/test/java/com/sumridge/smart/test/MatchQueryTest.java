package com.sumridge.smart.test;

import com.alibaba.fastjson.JSON;
import com.sumridge.smart.Application;
import com.sumridge.smart.entity.PortfolioInfo;
import com.sumridge.smart.query.MatchQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by zhujun on 2017/3/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MatchQueryTest {
    @Autowired
    private MatchQuery matchQuery;

    @Test
    public void testGetCusipList(){
        String id = "585a46419e532254a1772874";
        PortfolioInfo portfolioInfo = matchQuery.getCusipList(id);
        System.out.println(JSON.toJSONString(portfolioInfo));
    }

}
