package com.sgu.todo.repository;

import com.sgu.todo.entity.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskListRepository extends JpaRepository<TaskList,Integer> {
    public List<TaskList> findByFlgDelete(String flag);
}
