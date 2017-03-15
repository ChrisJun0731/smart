package com.sumridge.smart.controller;

import com.sumridge.smart.bean.ResultBean;
import com.sumridge.smart.domain.CurrentUser;
import com.sumridge.smart.entity.MatchInfo;
import com.sumridge.smart.entity.PortfolioInfo;
import com.sumridge.smart.service.MatchService;
import com.sumridge.smart.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by liu on 16/11/2.
 */
@RestController
@RequestMapping("/match")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @RequestMapping(value = "/list")
    public ResultBean getAccountList(Authentication authentication) {
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if (user != null) {
            ResultBean rs = matchService.getMatchList(user.getUserInfo());
            return rs;
        } else {
            return ResultBean.getSuccessResult();
        }
    }

    @RequestMapping(value = "/names")
    public ResultBean getNameList(Authentication authentication) {
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if (user != null) {
            ResultBean rs = matchService.getNameList(user.getUserInfo(), null);
            return rs;
        } else {
            return new ResultBean();
        }
    }

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public ResultBean addSimpleMatch(Authentication authentication, @RequestBody MatchInfo bean) {
        CurrentUser user = (CurrentUser) authentication.getPrincipal();
        if (user != null) {
            ResultBean rs = matchService.addSimpleMatch(user.getUserInfo(), bean);
            return rs;
        } else {
            return ResultBean.getSuccessResult();
        }

    }

    //load portfolio via emial 17/03/09
    @RequestMapping(value="/queryPortfolio")
    public ResultBean loadPortfolio(Authentication authentication, String email){
        //根据email查询出accountid
        List<String> accountIds = matchService.queryAccountIdByEmail(email);
        //根据accountid查询portfolio的一些字段信息
        return matchService.queryPortfolioByAccountId(accountIds);
    }

    @RequestMapping(value="/queryCusip")
    public ResultBean queryCusipByPortfolioId(String[] portfolioIds){
        return matchService.queryCusipListByPortfolioId(portfolioIds);
    }

    @RequestMapping(value="/smartMatch")
    public ResultBean getMktDataSimpleByCusip(String[] cusipSet){
        Set cusips = new HashSet();
        for(String cusip: cusipSet){
            cusips.add(cusip);
        }
        return matchService.getMktDataSimpleByCusip(cusips);
    }
}

