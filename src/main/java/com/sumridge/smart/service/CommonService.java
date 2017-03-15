package com.sumridge.smart.service;

import com.sumridge.smart.bean.ContactAggBean;
import com.sumridge.smart.bean.ResultBean;
import com.sumridge.smart.entity.SaleInfo;
import com.sumridge.smart.entity.UserInfo;
import com.sumridge.smart.query.CommonQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by zhujun on 2016/9/21.
 */
@Service
public class CommonService {

    @Autowired
    private CommonQuery commonQuery;

    //add by zj 16/9/21
    public void downloadFile(HttpServletResponse resp, String filename){
        ClassPathResource resource = new ClassPathResource("/templateFile/"+filename);
        resp.setHeader("Content-Disposition","attachment;filename="+filename);
        try{
            InputStream in = resource.getInputStream();
            OutputStream out = resp.getOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while((len=in.read(buffer)) > 0){
                out.write(buffer,0,len);
            }
            resp.flushBuffer();
            in.close();
            out.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public ResultBean getSortedList(UserInfo userInfo,int currentPage, int pageSize, String orderName, String order, String collection){
        if("saleInfo".equals(collection)){
            List<SaleInfo> list = commonQuery.getSortedSaleList(currentPage, pageSize,orderName, order);
            return ResultBean.getSuccessResult(list);
        }
        else if("contact".equals(collection)){
            List<ContactAggBean> list = commonQuery.getSortedContactList(userInfo,orderName, order);
            return ResultBean.getSuccessResult(list);
        }
        else{
            return null;
        }
    }
}
