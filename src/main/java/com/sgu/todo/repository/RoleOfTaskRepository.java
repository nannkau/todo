package com.sgu.todo.repository;

import com.sgu.todo.entity.Role;
import com.sgu.todo.entity.RoleOfTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleOfTaskRepository  extends JpaRepository<RoleOfTask,Integer> {
    public List<RoleOfTask> findByFlgDelete(String flag);
    public  List<RoleOfTask> findByCode(String code);
}
