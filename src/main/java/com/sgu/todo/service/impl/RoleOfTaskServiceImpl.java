package com.sgu.todo.service.impl;

import com.sgu.todo.entity.RoleOfTask;
import com.sgu.todo.repository.RoleOfTaskRepository;
import com.sgu.todo.service.RoleOfTaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleOfTaskServiceImpl implements RoleOfTaskService {
    private final RoleOfTaskRepository roleOfTaskRepository;

    public RoleOfTaskServiceImpl(RoleOfTaskRepository roleOfTaskRepository) {
        this.roleOfTaskRepository = roleOfTaskRepository;
    }

    @Override
    public List<RoleOfTask> findAll() {
        return roleOfTaskRepository.findFalseCreat();
    }

    @Override
    public RoleOfTask save(RoleOfTask roleOfTask) {
        return roleOfTaskRepository.save(roleOfTask);
    }

    @Override
    public void deleteById(Integer id) {
        RoleOfTask roleOfTask=roleOfTaskRepository.findById(id).get();
        roleOfTask.setFlgDelete("1");
        roleOfTaskRepository.save(roleOfTask);
    }

    @Override
    public Optional<RoleOfTask> findById(Integer id) {
        return roleOfTaskRepository.findById(id);
    }

    @Override
    public List<RoleOfTask> findByFlagDelete(String flag) {
        return roleOfTaskRepository.findByFlgDelete(flag);
    }

    @Override
    public List<RoleOfTask> getRoleOfTaskForUser(String email, Integer taskId) {
        return roleOfTaskRepository.getRoleOfTaskForUser(email,taskId);
    }
}
