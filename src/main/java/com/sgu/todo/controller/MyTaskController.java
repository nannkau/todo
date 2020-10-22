package com.sgu.todo.controller;

import com.sgu.todo.entity.Task;
import com.sgu.todo.service.TaskService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MyTaskController {
    final private TaskService taskService;

    public MyTaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping(value = "/my-task/public/index.html")
    public String publicTask(Model model, Authentication authentication){
        List<Task> tasks=taskService.findPublicTask(authentication.getName());
        model.addAttribute("tasks",tasks);
        model.addAttribute("date" ,new Date());
        return "my_task/public_task";
    }
    @RequestMapping(value = "/my-task/my/index.html")
    public String myTask(Model model, Authentication authentication){
        List<Task> tasks=taskService.findMyTask(authentication.getName());
        model.addAttribute("tasks",tasks);
        model.addAttribute("date" ,new Date());
        return "my_task/my_task";
    }
    @RequestMapping(value = "/my-task/invite/index.html")
    public String inviteTask(Model model, Authentication authentication){
        List<Task> tasks=taskService.findInviteTask(authentication.getName());
        model.addAttribute("tasks",tasks);
        model.addAttribute("date" ,new Date());
        return "my_task/invite_task";
    }
}
