package com.sgu.todo.service;

import com.sgu.todo.dto.TaskDTO;
import com.sgu.todo.entity.Comment;
import com.sgu.todo.entity.EditHistory;
import com.sgu.todo.entity.Task;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TaskService {
    public List<Task> findAll();
    public Task findTaskByIdAndFlgDelete(Integer id);
    public void create(TaskDTO taskDTO, HttpServletRequest request, Authentication authentication);
    public Task deleteById(Integer id,String email);
    public void addComment(Comment comment,Authentication authentication);
    public Task findById(Integer id);
    public List<Task> findByFlgDelete(String flgDelete);
    public List<Task> findMyTask(String email);
    public List<Task> findInviteTask(String email);
    public List<Task> findPublicTask(String email);
    public List<Task> findMyTaskByCode(String email,String code);
}
