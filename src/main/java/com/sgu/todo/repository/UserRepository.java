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

}
