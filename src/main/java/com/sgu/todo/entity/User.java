package com.sgu.todo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;
    @Column(name = "fullname", nullable = false)
    private String fullName;
    @Column(name = "email", nullable = false, unique = true )
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "flg_delete",length = 1)
    private String flgDelete;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
    @ManyToMany(mappedBy = "users",fetch = FetchType.LAZY)
    private List<Task> tasks;
    public Integer getId() {
        return id;
    }

    public void setId(@NotEmpty(message = "Name may not be empty")Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty(message = "Email may not be empty")String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty(message = "password may not be empty")String password) {
        this.password = password;
    }
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(@NotEmpty(message = "role may not be empty")Set<Role> roles) {
        this.roles = roles;
    }

    public String getFlgDelete() {
        return flgDelete;
    }

    public void setFlgDelete(String flgDelete) {
        this.flgDelete = flgDelete;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
