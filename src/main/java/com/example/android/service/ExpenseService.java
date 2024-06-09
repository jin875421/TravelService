package com.example.android.service;

import com.example.android.entity.Expense;
import com.example.android.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExpenseService {
    @Autowired
    ExpenseRepository expenseRepository;
    public String saveExpense(Expense expense) {
        expenseRepository.save(expense);
        return "111";
    }

    //通过expenseId获取进行删除
    @Transactional
    public void deleteExpense(String expenseId) {
        expenseRepository.deleteById(expenseId);
    }
    //通过userId进行查询
    public List<Expense> getExpenseByUserId(String userId) {
        return expenseRepository.findAllByUserId(userId);
    }
    //通过userId进行删除
    @Transactional
    public void deleteExpenseByUserId(String userId) {
        expenseRepository.deleteAllByUserId(userId);
    }
}
