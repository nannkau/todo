package com.sgu.todo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Table
@Entity
public class TaskList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_list_id")
    private Integer taskListId;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "name",length = 100)
    @Size(min = 1,max = 100,message = "Max long 100 char")
    @NotEmpty(message = "Enter name")
    private String name;
    @Column(name = "flg_delete",length = 1)
    private String flgDelete;
    @OneToMany(mappedBy = "taskList")
    private List<Task> tasks;
    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getTaskListId() {
        return taskListId;
    }

    public void setTaskListId(Integer taskListId) {
        this.taskListId = taskListId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public String getFlgDelete() {
        return flgDelete;
    }

    public void setFlgDelete(String flgDelete) {
        this.flgDelete = flgDelete;
    }
}
