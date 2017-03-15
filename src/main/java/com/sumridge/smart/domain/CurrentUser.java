package com.sumridge.smart.domain;

import com.sumridge.smart.entity.UserInfo;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

/**
 * Created by liu on 16/3/22.
 */
public class CurrentUser extends User {

    private UserInfo userInfo;

    public CurrentUser(UserInfo userInfo){
        super(userInfo.getEmail(), userInfo.getPassword(), AuthorityUtils.createAuthorityList(userInfo.getRole()));
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo(){
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
