package com.example.todo_app.repository;

import com.example.todo_app.entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<ToDo, Long> {
    public List<ToDo> findByUserId(Long userId);
}
