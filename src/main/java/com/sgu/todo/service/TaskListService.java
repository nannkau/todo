package com.sgu.todo.service;

import com.sgu.todo.entity.TaskList;

import java.util.List;
import java.util.Optional;

public interface TaskListService {
    public List<TaskList> findAll();
    public TaskList save(TaskList taskList);
    public void deleteById(Integer id);
    public Optional<TaskList> findById(Integer id);
    public List<TaskList> findByFlagDelete(String flag);
}