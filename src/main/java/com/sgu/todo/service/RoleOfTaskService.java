package com.sgu.todo.service;
import com.sgu.todo.entity.RoleOfTask;

import java.util.List;
import java.util.Optional;

public interface RoleOfTaskService {
    public List<RoleOfTask> findAll();
    public RoleOfTask save(RoleOfTask roleOfTask);
    public void deleteById(Integer id);
    public Optional<RoleOfTask> findById(Integer id);
    public List<RoleOfTask> findByFlagDelete(String flag);
}
