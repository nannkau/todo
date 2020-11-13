package com.sgu.todo.repository;

import com.sgu.todo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);
    User findById(int id);
    @Query(value="SELECT u FROM User u WHERE u.email <>:email ")
    List<User> findDifferentEmail(@Param("email") String email);
    @Query(value = "select * from user where user_id not in (select u.user_id from user_task_role_link as l ,task as t,user as u where l.task_id=t.task_id and l.user_id=u.user_id and l.task_id=:id)",nativeQuery = true)
    public List<User> findTaskByOtherUser(@Param("id") Integer id);

}
