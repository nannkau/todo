package com.sgu.todo.controller;

import com.sgu.todo.entity.Role;
import com.sgu.todo.entity.TaskList;
import com.sgu.todo.service.TaskListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Date;

@Controller
public class TaskListController {
    final  private TaskListService taskListService;
    @Autowired
    public TaskListController(TaskListService taskListService) {
        this.taskListService = taskListService;
    }

    @RequestMapping(value = "/task-list/index.html")
    public String index(Model model){
        model.addAttribute("taskLists",taskListService.findByFlagDelete("0"));
        return "task_list/index";
    }
    @RequestMapping(value = "/task-list/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id){
        TaskList taskList=taskListService.findById(id).get();
        Date date= new Date();
        model.addAttribute("date",date);
        model.addAttribute("taskList",taskList);
        return "task_list/detail";
    }

    @RequestMapping(value = "/task-list/add.html")
    public String add(Model model){
        TaskList taskList= new TaskList();
        model.addAttribute("taskList",taskList);
        return "task_list/add";
    }
    @RequestMapping(value = "/task-list/add.html",method = RequestMethod.POST)
    public String add(@Valid TaskList taskList, BindingResult result){
        if (result.hasErrors()) {
            return "task_list/add";
        }
        else {
            taskListService.save(taskList);
        }
        return "redirect:/task-list/index.html";
    }
    @RequestMapping(value = "/task-list/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id){
        TaskList taskList=taskListService.findById(id).get();
        model.addAttribute("taskList",taskList);
        return "task_list/edit";
    }
    @RequestMapping(value = "/task-list/delete/{id}")
    public String delete(Model model,@PathVariable("id") Integer id){

        taskListService.deleteById(id);
        return "redirect:/task-list/index.html";
    }

}
