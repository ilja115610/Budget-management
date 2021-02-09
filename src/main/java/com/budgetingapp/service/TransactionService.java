package com.budgetingapp.service;

import com.budgetingapp.domain.Budget;
import com.budgetingapp.domain.Category;
import com.budgetingapp.domain.Transaction;
import com.budgetingapp.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class TransactionService {


    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private CategoryService categoryService;


    public Transaction createNewTransaction (Long budgetId, Long categoryId) {

        Transaction tx = new Transaction();
        Budget budget = budgetService.findOne(budgetId);
        tx.setBudget(budget);
        budget.getTransactions().add(tx);
        tx.setDate(LocalDate.now());
        if(categoryId == null){

            return transactionRepository.save(tx);
        }
        else {
            Category category = categoryService.findOne(categoryId);
            category.getTransactions().add(tx);
            tx.setCategory(category);

            return transactionRepository.save(tx);
        }

    }

    public Transaction getOne (Long txId) {
        return transactionRepository.getOne(txId);
    }

    public Transaction saveOne (Transaction tx) {

        tx.setDate(tx.getDate());

        return transactionRepository.save(tx);
    }

}
