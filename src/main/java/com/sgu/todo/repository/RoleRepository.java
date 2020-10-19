package com.sgu.todo.repository;

import com.sgu.todo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    public List<Role> findByFlgDelete(String flag);
}
