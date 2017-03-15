package com.sumridge.smart.service;

import com.sumridge.smart.dao.UserInfoRepository;
import com.sumridge.smart.domain.CurrentUser;
import com.sumridge.smart.entity.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by liu on 16/3/22.
 */
@Service
public class LocalUserDetailsService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalUserDetailsService.class);

    @Autowired
    private UserInfoRepository repository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserInfo userInfo = repository.findByEmail(email);
        LOGGER.info("password:"+userInfo.getPassword());

        return new CurrentUser(userInfo);
    }
}
