package com.sumridge.smart.controller;

import com.sumridge.smart.bean.ResultBean;
import com.sumridge.smart.domain.CurrentUser;
import com.sumridge.smart.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhujun on 2016/9/21.
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    CommonService commonService;

    @RequestMapping("/download")
    public void downloadFile(HttpServletResponse resp, @RequestParam String filename) {
        commonService.downloadFile(resp,filename);
    }

    @RequestMapping("/sort")
    public ResultBean getSortedList(Authentication authentication, @RequestParam("currentPage") int currentPage,@RequestParam("pageSize") int pageSize,
                                    @RequestParam("orderName") String orderName,@RequestParam("order") String order,
                                    @RequestParam("collection") String collection){
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if (user != null) {
            ResultBean rs = commonService.getSortedList(user.getUserInfo(),currentPage,pageSize,orderName,order,collection);
            return rs;
        }
        else
            return new ResultBean();
    }
}
