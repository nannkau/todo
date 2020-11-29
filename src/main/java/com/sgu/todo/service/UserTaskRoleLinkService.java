package com.sgu.todo.service;

import com.sgu.todo.entity.User;
import com.sgu.todo.entity.UserTaskRoleLink;

import java.util.List;

public interface UserTaskRoleLinkService {
    public List<UserTaskRoleLink> findByTask(Integer id);
    public List<User> findByOtherTask(Integer id);
    public UserTaskRoleLink save(UserTaskRoleLink userTaskRoleLink);
    public void deleted(Integer id);
    public UserTaskRoleLink findById(Integer id);
    public User findUserCreateTask(Integer taskId);
}
