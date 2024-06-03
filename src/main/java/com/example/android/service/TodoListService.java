package com.example.android.service;

import com.example.android.entity.TodoItem;
import com.example.android.repository.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TodoListService {
    @Autowired
    private TodoItemRepository todoItemRepository;

    public void saveTodoItem(TodoItem todoItem) {
        todoItemRepository.save(todoItem);
    }

    @Transactional
    public void updateTodoItem(TodoItem todoItem) {
        if (todoItemRepository.existsById(todoItem.getId())) {
            todoItemRepository.save(todoItem);
        }
    }

    @Transactional
    public void deleteTodoItem(String todoId) {
        todoItemRepository.deleteById(todoId);
    }

    public List<TodoItem> getTodoItemsByUserId(String userId) {
        return todoItemRepository.findAllByUserId(userId);
    }

    @Transactional
    public void deleteTodoItemsByUserId(String userId) {
        todoItemRepository.deleteAllByUserId(userId);
    }
}
