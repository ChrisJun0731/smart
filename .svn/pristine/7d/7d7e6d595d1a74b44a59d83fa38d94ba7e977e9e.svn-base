package com.sumridge.smart.controller;

import com.sumridge.smart.entity.UserInfo;
import com.sumridge.smart.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liu on 16/3/21.
 */
@Controller
public class HomeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    protected AuthenticationManager authenticationManager;

    @RequestMapping(value = "/smart")
    public String getHome() {

        return "smart";
    }

//    @RequestMapping(value = "/customer")
//    public String getCustomerHome() {
//
//        return "customer";
//    }


    @RequestMapping(value = "/invite")
    public String doInvite(@RequestParam("id")String id,  HttpServletRequest request) {
        LOGGER.info("get invite from user");
        //TODO create user when access invite url
        UserInfo user =  userInfoService.getInviteUser(id);
        //TODO auto login after create
        authenticateUserAndSetSession(user, request);
        //TODO redirect to customer
        return "redirect:/login/";
    }

    private void authenticateUserAndSetSession(UserInfo user, HttpServletRequest request) {
        String username = user.getEmail();
        String password = "123@";
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        // generate session if one doesn't exist
        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }
}
