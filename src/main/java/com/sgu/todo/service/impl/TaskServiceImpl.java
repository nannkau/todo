package com.sgu.todo.service.impl;

import com.sgu.todo.dto.TaskDTO;
import com.sgu.todo.entity.*;
import com.sgu.todo.repository.CommentRepository;
import com.sgu.todo.repository.FileRepository;
import com.sgu.todo.repository.TaskRepository;
import com.sgu.todo.repository.UserRepository;
import com.sgu.todo.service.TaskService;
import com.sgu.todo.utils.FileUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Service
public class TaskServiceImpl implements TaskService {
    @Value("${upload.path}")
    private String outdir;
    final private UserRepository userRepository;
    final private TaskRepository taskRepository;
    final private CommentRepository commentRepository;
    final private  FileRepository fileRepository;
    @Autowired
    public TaskServiceImpl(UserRepository userRepository, TaskRepository taskRepository, FileRepository fileRepository, CommentRepository commentRepository) {
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

        List<File> files=new ArrayList<>();
        if (task.getFiles()!=null){
            files= task.getFiles();
        }

        MultipartFile[] parts=taskDTO.getParts();
       if (parts.length>0){
           try {
               List<String> paths= FileUtils.upload(outdir, request,parts);
               for (String path : paths){
                   File file= new File();
                   file.setPath(path);
                   file.setFlgDelete("0");
                   files.add(file);
               }

           } catch (IOException e) {
               e.printStackTrace();
           }
       }
        List<EditHistory> editHistories= new ArrayList<>();
       if (task.getEditHistories()!=null){
           editHistories=task.getEditHistories();
       }

       if(task.getTaskId()!=null){
           EditHistory history= new EditHistory();
           history.setCreateDate(new Date());
           history.setStatus("1"); // if value is 1 this is edit action
           history.setUser(user);
           editHistories.add(history);
       }
       else {
           EditHistory history= new EditHistory();
           history.setCreateDate(new Date());
           history.setStatus("0");
           history.setUser(user);
           editHistories.add(history);
       }
       task.setEditHistories(editHistories);
        task.setFlgDelete("0");
        task.setStartDate(new Date());
        task.setFiles(files);
        taskRepository.save(task);

    }

    @Override
    public void deleteById(Integer id) {
        Task task=findById(id);
        task.setFlgDelete("0");
        taskRepository.save(task);
    }

    @Override
    public void addComment(Comment comment) {
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
}
