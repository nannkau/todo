package com.sgu.todo.controller;

import com.sgu.todo.dto.TaskDTO;
import com.sgu.todo.entity.*;
import com.sgu.todo.service.TaskListService;
import com.sgu.todo.service.TaskService;
import com.sgu.todo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
public class TaskController {
    private final TaskService taskService;
    private final TaskListService taskListService;
    private final UserService userService;
    @Autowired
    public TaskController(TaskService taskService, TaskListService taskListService, UserService userService) {
        this.taskService = taskService;
        this.taskListService = taskListService;
        this.userService = userService;
    }

    @RequestMapping(value = "/task/add/{id}")
    public String add(Model model,  Authentication authentication,@PathVariable("id") Integer id){
        TaskDTO task= new TaskDTO();
        TaskList taskList=taskListService.findById(id).get();
        task.setTaskList(taskList);
        List<User> users=userService.findDifferentEmail(authentication.getName());
        model.addAttribute("users",users);
        model.addAttribute("task",task);
        return "task/add";
    }
    @RequestMapping(value = "/task/add.html",method = RequestMethod.POST)
    public String add(@Valid TaskDTO task, BindingResult result, HttpServletRequest request, Authentication authentication){
        if (result.hasErrors()) {
            return "task/add";
        }
        else {
            taskService.create(task,request,authentication);
        }
    return "redirect:/task-list/detail/"+task.getTaskList().getTaskListId().toString();
    }
    @RequestMapping(value = "/task/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id){

        Task task=taskService.findById(id);
        Date date= new Date();
        Comment comment= new Comment();
        comment.setTask(task);
        model.addAttribute("comment",comment);
        model.addAttribute("date",date);
        model.addAttribute("task",task);
        return "task/detail";
    }
    @RequestMapping(value = "/task/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id,Authentication authentication){
        Task task=taskService.findById(id);
        List<User> users=userService.findDifferentEmail(authentication.getName());
        ModelMapper modelMapper = new ModelMapper();
        TaskDTO taskDTO = modelMapper.map(task, TaskDTO.class);
        model.addAttribute("users",users);
        model.addAttribute("task",taskDTO);
        return "task/edit";
    }
    @RequestMapping(value = "/task/delete/{id}")
    public String delete(Model model,@PathVariable("id") Integer id){

      Task task= taskService.deleteById(id);
        return "redirect:/task-list/detail/"+task.getTaskList().getTaskListId().toString();
    }
    @RequestMapping(value = "/comment/add.html",method = RequestMethod.POST)
    public String addComment(@Valid Comment comment,BindingResult result,Authentication authentication)
    {

        if (result.hasErrors()) {
            return "task/detail"+comment.getTask().getTaskId().toString();
        }
        else {
            taskService.addComment(comment,authentication);
        }
        return "redirect:/task/detail/"+comment.getTask().getTaskId().toString();
    }
    @RequestMapping(value = "/task/history/{id}")
    public String listEditHistory(Model model,@PathVariable("id") Integer id){
        List<EditHistory> editHistoryList= taskService.findById(id).getEditHistories();
        model.addAttribute("editHistories",editHistoryList);
        return "task/edithistory";
    }


}
