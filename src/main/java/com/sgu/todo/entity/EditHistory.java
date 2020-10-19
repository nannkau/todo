package com.sgu.todo.entity;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "edit_history")
public class EditHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "edit_history_id")
    private Integer editHistoryId;
    @Column(name = "flg_detele")
    private String flgDelete;
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @OneToOne()
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private User user;

    public Integer getEditHistoryId() {
        return editHistoryId;
    }

    public void setEditHistoryId(Integer editHistoryId) {
        this.editHistoryId = editHistoryId;
    }

    public String getFlgDelete() {
        return flgDelete;
    }

    public void setFlgDelete(String flgDelete) {
        this.flgDelete = flgDelete;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    @ManyToOne()
    @JoinColumn(name = "task_id")
    private Task task;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
