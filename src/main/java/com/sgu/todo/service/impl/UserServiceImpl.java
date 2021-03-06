package com.sgu.todo.service.impl;

import com.sgu.todo.entity.User;
import com.sgu.todo.repository.UserRepository;
import com.sgu.todo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    final
    UserRepository userRepository;
    final
    PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {

        return userRepository.findAll();
    }

    @Override
    public User add(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        if (user.getId()!=null){
            if (!StringUtils.isNotBlank(user.getPassword())){
                User temp=userRepository.findById(user.getId()).get();
                user.setPassword(temp.getPassword());
            }
            else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        }
        else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Integer id) {
        User user = findById(id);
        user.setFlgDelete("1");
        userRepository.save(user);
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findDifferentEmail(String email) {
        return userRepository.findDifferentEmail(email);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findUserInTask(Integer id) {
        return userRepository.findUserByInUser(id);
    }

    @Override
    public List<User> findUserNotInTask(Integer id) {
        return userRepository.findUserByOtherTask(id);
    }
}
