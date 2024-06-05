package com.example.android.controller;

import com.example.android.entity.TodoItem;
import com.example.android.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoListController {
    @Autowired
    private TodoListService todoListService;

    @PostMapping("/save")
    public ResponseEntity<?> saveTodoItem(@RequestBody TodoItem todoItem) {
        try {
            todoListService.saveTodoItem(todoItem);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error saving todo item", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateTodoItem(@RequestBody TodoItem todoItem) {
        try {
            todoListService.updateTodoItem(todoItem);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating todo item", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/delete")
    public ResponseEntity<?> deleteTodoItemById(@RequestParam String id) {
        try {
            todoListService.deleteTodoItem(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting todo item", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/load")
    public ResponseEntity<List<TodoItem>> getTodoItemsByUserId(@RequestParam String userId) {
        try {
            List<TodoItem> todoItems = todoListService.getTodoItemsByUserId(userId);
            return new ResponseEntity<>(todoItems, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/clear")
    public ResponseEntity<?> deleteTodoItemsByUserId(@RequestParam String userId) {
        try {
            todoListService.deleteTodoItemsByUserId(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error clearing todo items", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
