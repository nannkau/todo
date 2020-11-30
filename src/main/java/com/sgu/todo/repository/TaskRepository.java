package com.sgu.todo.repository;

import com.sgu.todo.entity.Task;
import com.sgu.todo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {
    public List<Task> findByFlgDelete(String flgDelete);
    public List<Task> findTaskByPrivacy(String privacy);
    @Query("select t from Task t where t.taskId in (select t.taskId from UserTaskRoleLink utr join utr.roleOfTask r join utr.task t join utr.user u where r.code=:code and u.email=:email)")
    public List<Task> findMyTaskByCode(@Param("email") String email,@Param("code") String code);

}
