package com.sgu.todo.repository;

import com.sgu.todo.entity.Role;
import com.sgu.todo.entity.RoleOfTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RoleOfTaskRepository  extends JpaRepository<RoleOfTask,Integer> {
    public List<RoleOfTask> findByFlgDelete(String flag);
    public  List<RoleOfTask> findByCode(String code);
    @Query("select r from  UserTaskRoleLink utr join utr.user u join utr.task t join utr.roleOfTask r where u.email =:email and t.taskId=:taskId")
    public List<RoleOfTask> getRoleOfTaskForUser(@Param("email") String email,@Param("taskId") Integer taskId);
}
