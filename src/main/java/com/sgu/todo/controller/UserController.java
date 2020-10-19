package com.sgu.todo.controller;

import com.sgu.todo.entity.Role;
import com.sgu.todo.entity.User;
import com.sgu.todo.service.RoleService;
import com.sgu.todo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class UserController {
    private final UserService userService;
    private final RoleService roleService;


    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService=roleService;

    }
    @RequestMapping(value = "user/index.html")
    public String index(Model model){
        model.addAttribute("users",userService.findAll());
        return "user/index";
    }
    @RequestMapping(value = "/user/add.html")
    public String add(Model model){
        User user= new User();
        model.addAttribute("roles",roleService.findByFlagDelete("0"));
        model.addAttribute("user",user);
        return "user/add";
    }
    @RequestMapping(value = "/user/add.html",method = RequestMethod.POST)
    public String add(@Valid User user, BindingResult result){
        if (result.hasErrors()) {
            return "user/add";
        }
        else {
            userService.add(user);
        }
        return "redirect:/user/index.html";
    }
    @RequestMapping(value = "/user/edit/{id}")
    public String edit(Model model,@PathVariable("id") Integer id){
        User user = userService.findById(id);
        model.addAttribute("roles",roleService.findByFlagDelete("0"));
        model.addAttribute("user",user);
        return "user/edit";
    }
    @RequestMapping(value = "/user/delete/{id}")
    public String delete(Model model,@PathVariable("id") Integer id){


        userService.deleteById(id);
        return "redirect:/user/index.html";
    }


}
