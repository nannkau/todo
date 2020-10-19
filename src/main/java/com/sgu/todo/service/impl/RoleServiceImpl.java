package com.sgu.todo.service.impl;

import com.sgu.todo.entity.Role;
import com.sgu.todo.repository.RoleRepository;
import com.sgu.todo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository){
        this.roleRepository=roleRepository;
    }
    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void deleteById(Integer id) {
        Optional<Role> optionalRole = findById(id);
        Role role= new Role();
        role=optionalRole.get();
        role.setFlgDelete("1");
        roleRepository.save(role);
    }

    @Override
    public Optional<Role> findById(Integer id) {
        return roleRepository.findById(id);
    }

    @Override
    public List<Role> findByFlagDelete(String flag) {
        return roleRepository.findByFlgDelete(flag);
    }
}
