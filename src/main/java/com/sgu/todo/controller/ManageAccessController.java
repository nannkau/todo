package com.sgu.todo.controller;

import com.sgu.todo.entity.Task;
import com.sgu.todo.entity.User;
import com.sgu.todo.entity.UserTaskRoleLink;
import com.sgu.todo.service.RoleOfTaskService;
import com.sgu.todo.service.TaskService;
import com.sgu.todo.service.UserService;
import com.sgu.todo.service.UserTaskRoleLinkService;
import com.sgu.todo.utils.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ManageAccessController {
    final private TaskService taskService;
    final private UserService userService;
    final private RoleOfTaskService roleOfTaskService;

    final private UserTaskRoleLinkService userTaskRoleLinkService;


    public ManageAccessController(TaskService taskService, UserService userService, RoleOfTaskService roleOfTaskService, UserTaskRoleLinkService userTaskRoleLinkService) {
        this.taskService = taskService;
        this.userService = userService;
        this.roleOfTaskService = roleOfTaskService;
        this.userTaskRoleLinkService = userTaskRoleLinkService;
    }

    @RequestMapping("/manage-access/index/{id}")
    public String index(Model model, @PathVariable("id") Integer id){
        List<UserTaskRoleLink> userTaskRoleLinks=userTaskRoleLinkService.findByTask(id);

        if(userTaskRoleLinkService.findByTask(id)!=null){
            model.addAttribute("userTaskRoleLinks",userTaskRoleLinkService.findByTask(id));
        }
        model.addAttribute("taskId",id);
        return "manage_access/index";
    }
    @RequestMapping(value = "/manage-access/add/{id}")
    public String add(Model model, @PathVariable("id") Integer id){
        UserTaskRoleLink userTaskRoleLink= new UserTaskRoleLink();
        Task task=taskService.findById(id);
        userTaskRoleLink.setTask(task);
        List<User> users=userTaskRoleLinkService.findByOtherTask(id);
        model.addAttribute("users",userTaskRoleLinkService.findByOtherTask(id));
        model.addAttribute("roleOfTasks",roleOfTaskService.findAll());
        model.addAttribute("userTaskRoleLink",userTaskRoleLink);
        return "manage_access/add";
    }
    @RequestMapping(value = "/manage-access/add.html",method = RequestMethod.POST)
    public String add(@Valid UserTaskRoleLink userTaskRoleLink, BindingResult result){
        if (result.hasErrors()) {
            return "manage_access/add";
        }
        else {
            UserTaskRoleLink userTaskRoleLink1=userTaskRoleLinkService.save(userTaskRoleLink);
        }
        return "redirect:/manage-access/index/"+userTaskRoleLink.getTask().getTaskId().toString();
    }
    @RequestMapping(value = "/manage-access/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id){
        model.addAttribute("users",userTaskRoleLinkService.findByOtherTask(id));
        model.addAttribute("roleOfTasks",roleOfTaskService.findAll());
        UserTaskRoleLink userTaskRoleLink=userTaskRoleLinkService.findById(id);
        model.addAttribute("userTaskRoleLink",userTaskRoleLink);
        return "manage_access/add";
    }
    @RequestMapping(value = "/manage-access/delete/{id}")
    public String delete(Model model,@PathVariable("id") Integer id){

        userTaskRoleLinkService.deleted(id);
        return "redirect:/role/index.html";
    }
}
