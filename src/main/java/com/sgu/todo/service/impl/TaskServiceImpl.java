package com.sgu.todo.service.impl;

import com.sgu.todo.dto.TaskDTO;
import com.sgu.todo.entity.*;
import com.sgu.todo.repository.*;
import com.sgu.todo.service.TaskService;
import com.sgu.todo.utils.DateUtils;
import com.sgu.todo.utils.FileUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Service
public class TaskServiceImpl implements TaskService {
    @Value("${upload.path}")
    private String outdir;
    final private EditHistoryRepository editHistoryRepositor;
    final private UserRepository userRepository;
    final private TaskRepository taskRepository;
    final private CommentRepository commentRepository;
    final private  FileRepository fileRepository;
    final  private RoleOfTaskRepository roleOfTaskRepository;
    final private UserTaskRoleLinkRepository  userTaskRoleLinkRepository ;

    @Autowired
    public TaskServiceImpl(EditHistoryRepository editHistoryRepositor, UserRepository userRepository, TaskRepository taskRepository, FileRepository fileRepository, CommentRepository commentRepository, RoleOfTaskRepository roleOfTaskRepository, UserTaskRoleLinkRepository userTaskRoleLinkRepository) {
        this.editHistoryRepositor = editHistoryRepositor;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.fileRepository = fileRepository;
        this.commentRepository = commentRepository;
        this.roleOfTaskRepository = roleOfTaskRepository;
        this.userTaskRoleLinkRepository = userTaskRoleLinkRepository;
    }

    @Override
    public List<Task> findAll() {
       return taskRepository.findAll();
    }

    @Override
    public Task findTaskByIdAndFlgDelete(Integer id) {
        return taskRepository.findByTaskIdAndFlgDelete(id,"0").get(0);
    }

    @Override
    public void create(TaskDTO taskDTO, HttpServletRequest request, Authentication authentication) {
        ModelMapper modelMapper = new ModelMapper();
        Task task = modelMapper.map(taskDTO, Task.class);
        User user=userRepository.findByEmail(authentication.getName());
        if(task.getTaskId()==null){
            if(taskDTO.getParts().length>0){
                try {
                    task.setFiles(FileUtils.upload(outdir,request,taskDTO.getParts()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            task.setFlgDelete("0");
            EditHistory editHistory= new EditHistory();
            editHistory.setCreateDate(new Date());
            editHistory.setStatus("0");
            editHistory.setStatusDetail("Create by"+user.getFullName());
            editHistory.setFlgDelete("0");
            editHistory.setUser(user);
            List<EditHistory> editHistoryList=new ArrayList<>();
            editHistoryList.add(editHistory);
            task.setEditHistories(editHistoryList);
            task.setFlgDelete("0");
            task.setStartDate(new Date());
            RoleOfTask roleOfTask=roleOfTaskRepository.findByCode("CREATOR").get(0);
            UserTaskRoleLink userTaskRoleLink= new UserTaskRoleLink();
            userTaskRoleLink.setUser(user);
            userTaskRoleLink.setTask(task);
            userTaskRoleLink.setRoleOfTask(roleOfTask);
            userTaskRoleLinkRepository.save(userTaskRoleLink);

        }
        else {
            Task temp=taskRepository.findById(task.getTaskId()).get();
            task.setFlgDelete(temp.getFlgDelete());
            task.setComments(temp.getComments());
            List<File> fileList=new ArrayList<>();
            if(task.getFiles()!=null){
                fileList.addAll(temp.getFiles());
            }
            if(taskDTO.getParts()[0].getSize()>0){
                try {
                    fileList.addAll(FileUtils.upload(outdir,request,taskDTO.getParts()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            task.setFiles(fileList);
            task.setStartDate(temp.getStartDate());
            List<EditHistory> editHistoryList= temp.getEditHistories();
            EditHistory editHistory= new EditHistory();
            editHistory.setCreateDate(new Date());
            editHistory.setUser(user);
            editHistory.setStatus("1");
            StringBuffer buffer= new StringBuffer();
            buffer.append("Task change: ");
            if(!task.getContent().equals(temp.getContent())){
                buffer.append("\n");
                buffer.append("Change content ");
            }
            if(!task.getTitle().equals(temp.getTitle())){
                buffer.append("\n");
                buffer.append("Change title "+temp.getTitle()+" to "+task.getTitle());
            }
            if(!task.getPrivacy().equals(temp.getPrivacy())){

                String privacy="";
                if (task.getPrivacy().equals("1")){
                    privacy="public";
                }
                else {
                    privacy="private";
                }
                buffer.append("\n");
                buffer.append("Change Privacy to"+privacy);
            }
           if (task.getFiles()!=null&&temp.getFiles()!=null){
               if(temp.getFiles().size()!=task.getFiles().size()){
                   buffer.append("Change File");
               }
           }
           if ((task.getFiles()!=null&&temp.getFiles()==null)||(task.getFiles()==null&&temp.getFiles()!=null)){
               buffer.append("\n");
               buffer.append("Change file");
           }
           if (DateUtils.date2String(task.getFinishDate(),"dd/MM/yyyy").equals(DateUtils.date2String(temp.getFinishDate(),"dd/MM/yyyy"))){
               buffer.append("\n");
               buffer.append("Change finishDate "+ DateUtils.date2String(temp.getFinishDate(),"dd/MM/yyyy") +" to "+DateUtils.date2String(task.getFinishDate(),"dd/MM/yyyy"));
           }
            editHistory.setStatusDetail(buffer.toString());
            editHistory.setFlgDelete("0");
            editHistoryList.add(editHistory);
            task.setEditHistories(editHistoryList);
            taskRepository.save(task);

        }

    }

    @Override
    public Task deleteById(Integer id,String email) {
        User user= userRepository.findByEmail(email);
        Task task=findById(id);
        List<EditHistory> editHistoryList= task.getEditHistories();
        EditHistory editHistory= new EditHistory();
        editHistory.setCreateDate(new Date());
        editHistory.setUser(user);
        editHistory.setStatus("1");
        editHistory.setStatusDetail("Delete by "+user.getFullName());
        editHistoryList.add(editHistory);
        task.setFlgDelete("1");
        task.setEditHistories(editHistoryList);
        return taskRepository.save(task);
    }

    @Override
    public void addComment(Comment comment,Authentication authentication) {
        User user=userRepository.findByEmail(authentication.getName());
        comment.setCreateDate(new Date());
        comment.setUser(user);
        comment.setFlgDelete("0");
        commentRepository.save(comment);
    }

    @Override
    public Task findById(Integer id) {
        return taskRepository.findById(id).get();
    }

    @Override
    public List<Task> findByFlgDelete(String flgDelete) {
        return taskRepository.findByFlgDelete(flgDelete);
    }

    @Override
    public List<Task> findMyTask(String email) {
        List<Task> tempTasks= taskRepository.findByFlgDelete("1");

        return tempTasks;
    }

    @Override
    public List<Task> findInviteTask(String email) {
        List<Task> tempTasks= taskRepository.findByFlgDelete("1");
        return tempTasks;
    }

    @Override
    public List<Task> findPublicTask(String email) {
        List<Task> tempTasks= taskRepository.findTaskByPrivacyAndFlgDelete("1","0");
        return tempTasks;
    }

    @Override
    public List<Task> findMyTaskByCode(String email, String code) {
        return taskRepository.findMyTaskByCode(email,code);
    }


}
