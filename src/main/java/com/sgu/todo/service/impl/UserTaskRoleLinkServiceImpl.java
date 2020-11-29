package com.sgu.todo.service.impl;

import com.sgu.todo.entity.User;
import com.sgu.todo.entity.UserTaskRoleLink;
import com.sgu.todo.repository.UserTaskRoleLinkRepository;
import com.sgu.todo.service.UserTaskRoleLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserTaskRoleLinkServiceImpl implements UserTaskRoleLinkService {
    final private  UserTaskRoleLinkRepository userTaskRoleLinkRepository;
    @Autowired
    public UserTaskRoleLinkServiceImpl(UserTaskRoleLinkRepository userTaskRoleLinkRepository) {
        this.userTaskRoleLinkRepository = userTaskRoleLinkRepository;
    }

    @Override
    public List<UserTaskRoleLink> findByTask(Integer id) {
        try {
            if(userTaskRoleLinkRepository.findByTask(id)!=null){
                return userTaskRoleLinkRepository.findByTask(id);
            }

            return null;
        }
        catch (NullPointerException e){
            throw e;
        }

    }

    @Override
    public List<User> findByOtherTask(Integer id) {
        return userTaskRoleLinkRepository.findUserByOtherTask(id);
    }

    @Override
    public UserTaskRoleLink save(UserTaskRoleLink userTaskRoleLink) {
        return userTaskRoleLinkRepository.save(userTaskRoleLink);
    }

    @Override
    public void deleted(Integer id) {
        userTaskRoleLinkRepository.deleteById(id);
    }

    @Override
    public UserTaskRoleLink findById(Integer id) {
        return userTaskRoleLinkRepository.findById(id).get();
    }

    @Override
    public User findUserCreateTask(Integer taskId) {
        return userTaskRoleLinkRepository.findByUserCodeAndTask(taskId,"CREATOR").get(0);
    }
}
