package com.sumridge.smart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

/**
 * Created by liu on 16/3/21.
 */
@Controller
public class SystemController {

    /**
     * get login page
     * @param error
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLoginPage(@RequestParam Optional<String> error) {
        return new ModelAndView("login", "error", error);
    }

    /**
     * home page
     * @param model
     * @return
     */
    @RequestMapping("/")
    public String getHomePage(Model model) {
        return "redirect:/smart ";
    }



}
