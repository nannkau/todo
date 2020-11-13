package com.sgu.todo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;
@Entity
@Table(name = "role_of_task")
public class RoleOfTask implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_of_task_id", nullable = false)
    private Integer id;
    @Column(name = "name", nullable = false)
    @NotEmpty(message = "Enter name")
    @Size(max = 50,message = " max size is 50 char")
    private String name;
    @Column(name = "code", nullable = false)
    @NotEmpty(message = "Enter code")
    @Size(max = 50,message = " max size is 50 char")
    private String code;
    @Column(name = "detail", nullable = false)
    private String detail;
    @Column(name = "flg_delete",length = 1)
    private String flgDelete;
    @OneToMany(mappedBy = "roleOfTask", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<UserTaskRoleLink> userTaskRoleLinks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getFlgDelete() {
        return flgDelete;
    }

    public void setFlgDelete(String flgDelete) {
        this.flgDelete = flgDelete;
    }

    public Set<UserTaskRoleLink> getUserTaskRoleLinks() {
        return userTaskRoleLinks;
    }

    public void setUserTaskRoleLinks(Set<UserTaskRoleLink> userTaskRoleLinks) {
        this.userTaskRoleLinks = userTaskRoleLinks;
    }
}
