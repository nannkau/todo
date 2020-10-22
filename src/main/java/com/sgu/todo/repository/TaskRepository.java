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
    @Query(value="SELECT t from Task t join t.users u where  u.email = :email")
    public List<Task> findTaskByEmail(@Param("email") String email);
    public List<Task> findTaskByPrivacy(String privacy);

}
