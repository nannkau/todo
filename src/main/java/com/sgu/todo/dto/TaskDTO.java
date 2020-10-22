package com.sgu.todo.dto;

import com.sgu.todo.entity.TaskList;
import com.sgu.todo.entity.User;
import org.springframework.web.multipart.MultipartFile;


import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

public class TaskDTO {

    private Integer taskId;

    @NotEmpty(message = "Enter title")
    private String title;
    private String content;

    private Date startDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date finishDate;

    @NotEmpty(message = "Enter privacy")
    private String privacy;

    @NotEmpty(message = "Enter status")
    private String status;

    private String flgDelete;
    private List<User> users;
    private TaskList taskList;

    public TaskList getTaskList() {
        return taskList;
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    private  MultipartFile[] parts;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFlgDelete() {
        return flgDelete;
    }

    public void setFlgDelete(String flgDelete) {
        this.flgDelete = flgDelete;
    }

    public MultipartFile[] getParts() {
        return parts;
    }

    public void setParts(MultipartFile[] parts) {
        this.parts = parts;
    }
}
