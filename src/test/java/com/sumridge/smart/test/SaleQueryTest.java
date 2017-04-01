package com.sumridge.smart.test;

/**
 * Created by zhujun on 2017/3/28.
 */

import com.alibaba.fastjson.JSON;
import com.sumridge.smart.Application;
import com.sumridge.smart.entity.SaleInfo;
import com.sumridge.smart.query.SaleQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class SaleQueryTest {
    @Autowired
    private SaleQuery saleQuery;

    @Test
    public void salesFuzzyQueryTest(){
//        List<SaleInfo> saleInfoList = saleQuery.salesFuzzyQuery("103304");
//        System.out.println(JSON.toJSONString(saleInfoList));
    }
}
