package com.sgu.todo.service;

import com.sgu.todo.dto.TaskDTO;
import com.sgu.todo.entity.Comment;
import com.sgu.todo.entity.Task;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TaskService {
    public List<Task> findAll();
    public void create(TaskDTO taskDTO, HttpServletRequest request);
    public void deleteById(Integer id);
    public void addComment(Comment comment);
    public Task findById(Integer id);
}
