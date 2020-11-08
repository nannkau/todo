package com.sgu.todo.service.impl;

import com.sgu.todo.dto.TaskDTO;
import com.sgu.todo.entity.*;
import com.sgu.todo.repository.*;
import com.sgu.todo.service.TaskService;
import com.sgu.todo.utils.FileUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Autowired
    public TaskServiceImpl(EditHistoryRepository editHistoryRepositor, UserRepository userRepository, TaskRepository taskRepository, FileRepository fileRepository, CommentRepository commentRepository) {
        this.editHistoryRepositor = editHistoryRepositor;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.fileRepository = fileRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Task> findAll() {
       return taskRepository.findAll();
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
            List<User> users= new ArrayList<>();
            users.add(user);
            if(task.getUsers() !=null){
                for (User user1:
                        task.getUsers()) {
                    users.add(user1);
                }
            }
            task.setUsers(users);
            EditHistory editHistory= new EditHistory();
            editHistory.setCreateDate(new Date());
            editHistory.setUser(user);
            editHistory.setStatus("0");
            List<EditHistory> editHistoryList=new ArrayList<>();
            editHistoryList.add(editHistory);
            task.setEditHistories(editHistoryList);
            task.setFlgDelete("0");
            task.setStartDate(new Date());
        }
        else {
            Task temp=taskRepository.findById(task.getTaskId()).get();
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
            User userCreate=temp.getUsers().get(0);
            List<User> userList=new ArrayList<>();
            userList.add(temp.getUsers().get(0));
            if(task.getUsers()!=null){
                userList.addAll(task.getUsers());
            }
            List<EditHistory> editHistoryList= temp.getEditHistories();
            EditHistory editHistory= new EditHistory();
            editHistory.setCreateDate(new Date());
            editHistory.setUser(user);
            editHistoryList.add(editHistory);
            task.setEditHistories(editHistoryList);
            task.setFiles(fileList);
            task.setUsers(userList);
            task.setStartDate(temp.getStartDate());

        }
        taskRepository.save(task);
    }

    @Override
    public Task deleteById(Integer id) {
        Task task=findById(id);
        task.setFlgDelete("0");
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
        List<Task> tempTasks= taskRepository.findTaskByEmail(email);
        List<Task> tasks= new ArrayList<>();
        for (Task task:
             tempTasks) {
            if(task.getUsers().get(0).getEmail().equals(email)) tasks.add(task);

        }
        return tasks;
    }

    @Override
    public List<Task> findInviteTask(String email) {
        List<Task> tempTasks= taskRepository.findTaskByEmail(email);
        List<Task> tasks= new ArrayList<>();
        for (Task task:
                tempTasks) {
            if(!task.getUsers().get(0).getEmail().equals(email) )tasks.add(task);

        }
        return tasks;
    }

    @Override
    public List<Task> findPublicTask(String email) {
        List<Task> tempTasks= taskRepository.findTaskByPrivacy("1");
        List<Task> tasks= new ArrayList<>();
        for (Task task:
                tempTasks) {
            if(!task.getUsers().get(0).getEmail().equals(email) )tasks.add(task);

        }
        return tasks;
    }


}
