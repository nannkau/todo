package com.sgu.todo.entity;

import com.sun.istack.NotNull;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.ManyToAny;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "task")
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Integer taskId;
    @Column(name = "title",length = 250)
    private String title;
    @Column(name = "content",length = 16777215)
    private String content;
    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "finish_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finishDate;
    @Column(name = "privacy",length = 1)
    private String privacy;
    @Column(name = "status",length = 1)
    private String status;
    @Column(name = "flg_delete",length = 1)
    private String flgDelete;
    @OneToMany(fetch= FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id")
    private List<File> files;
    @OneToMany(fetch= FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id")
    private List<EditHistory> editHistories;
    @OneToMany(fetch= FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id")
    private List<Comment> comments;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "task_user",joinColumns = @JoinColumn(name = "task_id"),inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;
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

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public List<EditHistory> getEditHistories() {
        return editHistories;
    }

    public void setEditHistories(List<EditHistory> editHistories) {
        this.editHistories = editHistories;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
