package com.sumridge.smart.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sumridge.smart.bean.AccountAggBean;
import com.sumridge.smart.bean.ContactAggBean;
import com.sumridge.smart.bean.ResultBean;
import com.sumridge.smart.domain.CurrentUser;
import com.sumridge.smart.entity.Contact;
import com.sumridge.smart.entity.UserInfo;
import com.sumridge.smart.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.sumridge.smart.bean.FieldBean;

import java.util.*;

/**
 * Created by liu on 16/5/11.
 */
@RestController
@RequestMapping("/contact")
public class ContactController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContactController.class);
    //初级加载contact的数量。
    private final int LIMIT = 5;
    //每次额外加载5条信息。
    private final int INCREMENT = 5;
    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "/list")
    public ResultBean getAccountList(Authentication authentication) {
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if (user != null) {
            List<ContactAggBean> contactList = new ArrayList();
            //查询所有contact的首字母集合
            List<Map> initials = contactService.queryInitials();
            //根据每个首字母查询下面的contacts
            for(Map initial: initials){
                contactList.addAll(contactService.queryInitialContacts((String)initial.get("_id"), user.getUserInfo(), LIMIT));
            }
            ResultBean rs = ResultBean.getSuccessResult(contactList);
            return rs;
        } else {
            return new ResultBean();
        }
    }

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public ResultBean saveContact(Authentication authentication,@RequestBody Contact info) {
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        UserInfo userInfo = user.getUserInfo();
        if (user != null) {
            if(info.getId() == null) {
                info.setCreateDate(new Date());
                info.setCreator(user.getUserInfo().getId());
                info.setOwner(user.getUserInfo().getId());
            }
            ResultBean rs = contactService.saveContactInfo(userInfo,info);
            return rs;
        } else {
            return new ResultBean();
        }

    }

    @RequestMapping(value = "/invite", method = RequestMethod.POST)
    public ResultBean inviteContact(Authentication authentication,@RequestBody Contact info) {
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        UserInfo userInfo = user.getUserInfo();
        if (user != null) {

            ResultBean rs = contactService.inviteContactInfo(info,userInfo);
            return rs;
        } else {
            return new ResultBean();
        }

    }

    @RequestMapping(value = "/info")
    public ResultBean getCompany(@RequestParam("id") String contactId) {
        ResultBean rs = contactService.getContact(contactId);
        return rs;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public ResultBean uploadFile(Authentication authentication, @RequestParam("file") MultipartFile file, @RequestParam("privilege") String privilege) {
        LOGGER.info("file:"+file.getName()+"/"+file.getContentType()+"/"+file.getOriginalFilename()+"/"+file.getSize());
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if (user != null) {
            ResultBean rs = contactService.saveFile(user.getUserInfo(), file, privilege);
            return rs;
        } else {
            return new ResultBean();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/map-upload")
    public ResultBean uploadMapFile(Authentication authentication, @RequestParam("file") MultipartFile file) {
        LOGGER.info("file:"+file.getName()+"/"+file.getContentType()+"/"+file.getOriginalFilename()+"/"+file.getSize());
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if (user != null) {
            ResultBean rs = contactService.saveMapFile(user.getUserInfo(), file);
            return rs;
        } else {
            return new ResultBean();
        }
    }

    @RequestMapping(value="/field")
    public ResultBean saveFormField(Authentication authentication, @RequestParam("customFields")String customFields){
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if(user != null){
            return contactService.saveFormField(customFields);
        }
        else{
            return new ResultBean();
        }
    }

    //add by zj 17/01/03
    @RequestMapping("/contactNames")
    public ResultBean getContactNames(Authentication authentication){
        CurrentUser currentUser = (CurrentUser)authentication.getPrincipal();
        if(currentUser != null){
            return contactService.getContactNames();
        }
        else{
            return new ResultBean();
        }
    }

    @RequestMapping("/loadMoreContacts")
    public ResultBean loadMoreContacts(Authentication authentication, String initial, int contactNum){
        CurrentUser currentUser = (CurrentUser)authentication.getPrincipal();
        if(currentUser != null){
            List<ContactAggBean> initialContacts = contactService.queryInitialContacts(initial, currentUser.getUserInfo(), contactNum+INCREMENT);
            ResultBean rs = ResultBean.getSuccessResult(initialContacts.get(0));
            return rs;
        }
        else{
            return new ResultBean();
        }
    }

}
