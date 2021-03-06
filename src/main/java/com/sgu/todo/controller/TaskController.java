package com.sgu.todo.controller;

import com.sgu.todo.dto.TaskDTO;
import com.sgu.todo.entity.*;
import com.sgu.todo.service.*;
import com.sgu.todo.utils.Constants;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class TaskController {
    @Value("${upload.path}")
    private String outdir;
    private final FileService fileService;
    private final TaskService taskService;
    private final UserService userService;
    private final RoleOfTaskService roleOfTaskService;
    private final UserTaskRoleLinkService userTaskRoleLinkService;
    @Autowired
    public TaskController(FileService fileService, TaskService taskService, UserService userService, RoleOfTaskService roleOfTaskService, UserTaskRoleLinkService userTaskRoleLinkService) {
        this.fileService = fileService;
        this.taskService = taskService;
        this.userService = userService;
        this.roleOfTaskService = roleOfTaskService;
        this.userTaskRoleLinkService = userTaskRoleLinkService;
    }
    @RequestMapping(value = "/task-all/index.html")
    public String index(Model model){
        model.addAttribute("tasks",taskService.findByFlgDelete("0"));
        model.addAttribute("date",new Date());
        return "task/index";
    }
    @RequestMapping(value = "/task/add.html")
    public String add(Model model,  Authentication authentication){
        TaskDTO task= new TaskDTO();
        List<User> users=userService.findDifferentEmail(authentication.getName());
        model.addAttribute("users",users);
        model.addAttribute("taskDTO",task);
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
        if (checkRole(authentication.getName())){
            return "redirect:/task-all/index.html";
        }
        return "redirect:/my-task/my/index.html";
    }
    @RequestMapping(value = "/task/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id,Authentication authentication){
       if (roleOfTaskService.getRoleOfTaskForUser(authentication.getName(),id).size()>0){
           RoleOfTask roleOfTask=roleOfTaskService.getRoleOfTaskForUser(authentication.getName(),id).get(0);
           model.addAttribute(Constants.ROLE_OF_TASK,roleOfTask.getCode());
       }
        Task task=taskService.findTaskByIdAndFlgDelete(id);
       if (task.getPrivacy().equals("1")){
           model.addAttribute(Constants.ROLE_OF_TASK,"public");
       }
       for (Role role :userService.findByEmail(authentication.getName()).getRoles()){
           if (role.getName().equals("admin")){
               model.addAttribute(Constants.ROLE_OF_TASK,"admin");
           }
       }
        User user=userTaskRoleLinkService.findUserCreateTask(id);
        Date date= new Date();
        Comment comment= new Comment();
        comment.setTask(task);
        model.addAttribute("comment",comment);
        model.addAttribute("date",date);
        model.addAttribute("task",task);
        model.addAttribute("user",user);
        return "task/detail";
    }
    @RequestMapping(value = "/task/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id,Authentication authentication){
        if (roleOfTaskService.getRoleOfTaskForUser(authentication.getName(),id).size()>0){
            RoleOfTask roleOfTask=roleOfTaskService.getRoleOfTaskForUser(authentication.getName(),id).get(0);
            model.addAttribute(Constants.ROLE_OF_TASK,roleOfTask.getCode());
        }
        for (Role role :userService.findByEmail(authentication.getName()).getRoles()){
            if (role.getName().equals("admin")){
                model.addAttribute(Constants.ROLE_OF_TASK,"admin");
            }
        }
        Task task=taskService.findTaskByIdAndFlgDelete(id);
        List<User> users=userService.findDifferentEmail(authentication.getName());
        ModelMapper modelMapper = new ModelMapper();
        TaskDTO taskDTO = modelMapper.map(task, TaskDTO.class);
        model.addAttribute("users",users);
        model.addAttribute("taskDTO",taskDTO);
        return "task/edit";
    }
    @RequestMapping(value = "/task/delete/{id}")
    public String delete(Model model,@PathVariable("id") Integer id,Authentication authentication){

      Task task= taskService.deleteById(id,authentication.getName());
        if (checkRole(authentication.getName())){
            return "redirect:/task-all/index.html";
        }
        return "redirect:/my-task/my/index.html";
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
    @RequestMapping(value = "/file/download/{id}")
    public void downloadFile(@PathVariable("id") Integer id, HttpServletResponse resp) throws IOException {
        File file= fileService.findById(id);
        java.io.File  file1= new java.io.File(outdir+"/"+file.getPath());
        resp.setContentType(FilenameUtils.getExtension(outdir+"/"+file.getPath()));
        resp.setHeader("Content-disposition", "attachment; filename=" + file.getName());
        resp.setContentLength((int) file1.length());
        BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(file1));
        BufferedOutputStream outStream = new BufferedOutputStream(resp.getOutputStream());
        byte[] buffer = new byte[1024];
        int bytesRead = 0;
        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
        outStream.flush();
        inStream.close();

    }
    private boolean checkRole(String email){
        User user=userService.findByEmail(email);
        for (Role role :user.getRoles()){
            if (role.getName().equals("admin")) return true;
        }
        return false;
    }


}
