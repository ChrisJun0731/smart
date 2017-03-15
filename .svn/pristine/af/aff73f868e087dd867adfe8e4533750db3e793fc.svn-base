package com.sumridge.smart.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sumridge.smart.Application;
import com.sumridge.smart.entity.FileViewer;
import com.sumridge.smart.util.FileTypeUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liu on 16/4/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TestFileTypeTest {

//    @Test
//    public void testFileType() {
//        FileViewer viewer = FileTypeUtil.getFileType("text/javascrip","app.js");
//        Assert.assertEquals("js",viewer.getType());
//    }

    @Test
    public void DateWithoutTimeTest() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse("2015-05-23");
        System.out.println(JSON.toJSONString(date, SerializerFeature.UseISO8601DateFormat));


    }
}
