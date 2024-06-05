package com.example.android.repository;

import com.example.android.entity.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem, String> {
    List<TodoItem> findAllByUserId(String userId);

    int deleteAllByUserId(String userId);
}
