package com.sgu.todo.service;

import com.sgu.todo.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService  {
    public List<Role> findAll();
    public Role save(Role role);
    public void deleteById(Integer id);
    public Optional<Role> findById(Integer id);
    public List<Role> findByFlagDelete(String flag);

}

