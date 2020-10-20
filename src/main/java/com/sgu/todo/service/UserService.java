package com.sgu.todo.service;

import com.sgu.todo.entity.User;

import java.util.List;

public interface UserService {
    public List<User> findAll();
    public User add(User user);
    public User update(User user);
    public void deleteById(Integer id);
    public User findById(int id);
    public List<User> findDifferentEmail(String email);

}