package com.sgu.todo.repository;

import com.sgu.todo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {
    public List<Task> findByFlgDelete(String flgDelete);
}
