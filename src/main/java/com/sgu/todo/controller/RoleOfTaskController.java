package com.sgu.todo.controller;

import com.sgu.todo.entity.Role;
import com.sgu.todo.entity.RoleOfTask;
import com.sgu.todo.service.RoleOfTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class RoleOfTaskController {
    private final RoleOfTaskService roleOfTaskService;
    @Autowired
    public RoleOfTaskController(RoleOfTaskService roleOfTaskService) {
        this.roleOfTaskService = roleOfTaskService;
    }


    @RequestMapping(value = "/role-of-task/index.html")
    public String index(Model model){
        model.addAttribute("roleOfTasks",roleOfTaskService.findAll());
        return "role_of_task/index";
    }

    @RequestMapping(value = "/role-of-task/add.html")
    public String add(Model model){
        RoleOfTask roleOfTask= new RoleOfTask();
        model.addAttribute("roleOfTask",roleOfTask);
        return "role_of_task/add";
    }
    @RequestMapping(value = "/role-of-task/add.html",method = RequestMethod.POST)
    public String add(@Valid RoleOfTask RoleOfTask, BindingResult result){
        if (result.hasErrors()) {
            return "role_of_task/add";
        }
        else {
            roleOfTaskService.save(RoleOfTask);
        }
        return "redirect:/role-of-task/index.html";
    }
    @RequestMapping(value = "/role-of-task/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id){
        RoleOfTask roleOfTask=roleOfTaskService.findById(id).get();
        model.addAttribute("roleOfTask",roleOfTask);
        return "role_of_task/edit";
    }
    @RequestMapping(value = "/role-of-task/delete/{id}")
    public String delete(Model model,@PathVariable("id") Integer id){

        roleOfTaskService.deleteById(id);
        return "redirect:/role-of-task/index.html";
    }
}
