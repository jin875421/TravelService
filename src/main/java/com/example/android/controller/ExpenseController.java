package com.example.android.controller;

import com.example.android.entity.Expense;
import com.example.android.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expense")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;



    @PostMapping("/save")
    public ResponseEntity<?> saveExpense(@RequestBody Expense expense) {
        try {
            expenseService.saveExpense(expense);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error saving expense", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/delete")
    public boolean deleteExpenseById(@RequestParam String id) {
        expenseService.deleteExpense(id);
        System.out.println("2222");
        return true;
    }

    @GetMapping("/load")
    public List<Expense> getExpenseByUserId(@RequestParam String userId) {
        return expenseService.getExpenseByUserId(userId);
    }

    @GetMapping("/clear")
    public boolean deleteExpenseByUserId(@RequestParam String userId) {
        expenseService.deleteExpenseByUserId(userId);
        return true;
    }
}
