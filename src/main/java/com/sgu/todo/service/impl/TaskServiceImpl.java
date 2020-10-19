package com.sgu.todo.service.impl;

import com.sgu.todo.dto.TaskDTO;
import com.sgu.todo.entity.Comment;
import com.sgu.todo.entity.File;
import com.sgu.todo.entity.Task;
import com.sgu.todo.repository.CommentRepository;
import com.sgu.todo.repository.FileRepository;
import com.sgu.todo.repository.TaskRepository;
import com.sgu.todo.service.TaskService;
import com.sgu.todo.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskServiceImpl implements TaskService {
    @Value("${upload.path}")
    private String outdir;
    final private TaskRepository taskRepository;
    final private CommentRepository commentRepository;
    final private  FileRepository fileRepository;
    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, FileRepository fileRepository, CommentRepository commentRepository) {
        this.taskRepository = taskRepository;
        this.fileRepository = fileRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Task> findAll() {
       return taskRepository.findAll();
    }

    @Override
    public void create(TaskDTO taskDTO, HttpServletRequest request) {
        Task task= new Task();
        List<File> files= new ArrayList<File>();
        try {
            List<String> paths= FileUtils.upload(outdir, request, taskDTO.getParts());
            for (String path : paths){
                File file= new File();
                file.setPath(path);
                file.setFlgDelete("0");
                files.add(file);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        task.setTitle(taskDTO.getTitle());
        task.getFinishDate(taskDTO.getFinishDate());
        task.setFlgDelete("0");
        task.setStartDate(new Date());
        task.setFiles(files);

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
}
