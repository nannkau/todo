package com.sgu.todo.repository;

import com.sgu.todo.entity.User;
import com.sgu.todo.entity.UserTaskRoleLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTaskRoleLinkRepository extends JpaRepository<UserTaskRoleLink,Integer> {
    @Query(value = "delete from user_task_role_link where task_id=:taskId and user_id=:userId",nativeQuery = true)
    public void deleteByUserId(@Param("taskId") Integer taskId,@Param("userId") Integer userId);
    @Query("select utr from UserTaskRoleLink utr join utr.task t join utr.user u where t.taskId =:id")
    public List<UserTaskRoleLink> findByTask(@Param("id") Integer id);
    @Query("select u from UserTaskRoleLink utr join utr.task t join utr.user u where t.taskId <>:id")
    public List<User> findUserByOtherTask(@Param("id") Integer id);
    @Query("select u from UserTaskRoleLink utr join utr.user u join utr.task t join utr.roleOfTask r where r.code=:code and t.taskId=:taskId")
    public List<User> findByUserCodeAndTask(@Param("taskId") Integer taskId,@Param("code") String code);
}
