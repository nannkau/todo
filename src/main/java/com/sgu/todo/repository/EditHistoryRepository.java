package com.sgu.todo.repository;

import com.sgu.todo.entity.EditHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditHistoryRepository extends JpaRepository<EditHistory,Integer> {
}
