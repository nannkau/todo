package com.sgu.todo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {
    @RequestMapping(value = {"/","index.html"})
    public String index(){
        return "home/index";
    }
    @RequestMapping(value = {"/admin"})
    public String admin(Model model){
        UserDetails user =(UserDetails) (SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        user.getUsername();

        return  "redirect:/index.html?q="+user.getUsername();
    }

    @RequestMapping(value = {"/staff"})
    public String user(Model model){
        UserDetails user =(UserDetails) (SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        user.getUsername();

        return  "redirect:/index.html?q="+user.getUsername();
    }
    @RequestMapping(value = {"/seller"})
    public String seller(Model model){
        UserDetails user =(UserDetails) (SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        user.getUsername();

        return  "redirect:/index.html?q="+user.getUsername();
    }
    @RequestMapping(value = {"/accessdenied"})
    public String accessdenied(){
        return "accessdenied";
    }
    @RequestMapping(value="/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }
//    @RequestMapping(value = "/error")
//    public String error(){
//        return "home/error";
//    }
}
