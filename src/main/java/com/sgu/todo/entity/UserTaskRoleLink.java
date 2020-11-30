package com.sgu.todo.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_task_role_link")
public class UserTaskRoleLink implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinColumn(name = "role_of_task_id")
    private RoleOfTask roleOfTask;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public RoleOfTask getRoleOfTask() {
        return roleOfTask;
    }

    public void setRoleOfTask(RoleOfTask roleOfTask) {
        this.roleOfTask = roleOfTask;
    }
}
