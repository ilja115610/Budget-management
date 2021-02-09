package com.budgetingapp.controllers;

import com.budgetingapp.domain.Category;
import com.budgetingapp.domain.Transaction;
import com.budgetingapp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = {"/budgets/{budgetId}/transactions",
                "/budgets/{budgetId}/groups/{groupId}/categories/{categoryId}/transactions"})
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("")
    public String addTransactionToBudget (@PathVariable Long budgetId, @PathVariable(required=false) Long groupId,
                                          @PathVariable(required=false) Long categoryId) {

        Transaction tx = transactionService.createNewTransaction(budgetId,categoryId);
        String url = "";

        if(categoryId != null){
            url = "/budgets/"+budgetId+"/groups/"+groupId+"/categories/"+categoryId+"/transactions";
        }
        else {
            url = "/budgets/"+budgetId+"/transactions";
        }

        return "redirect:"+url+ "/" +tx.getId();
    }


    @GetMapping("{transactionId}")
    public String getTransaction (@PathVariable Long transactionId,@PathVariable Long budgetId, Model model) {

        Transaction tx = transactionService.getOne(transactionId);
        model.addAttribute("transaction",tx);
        model.addAttribute("budget",tx.getBudget());
        List<Category> categories = tx.getBudget().getGroups().stream().flatMap(g->g.getCategories()
                .stream()).collect(Collectors.toList());
        model.addAttribute("categories",categories);

        return "transaction";
    }


    @PostMapping("{transactionId}")
    public String saveTransaction (@ModelAttribute Transaction transaction) {

        Transaction tx = transactionService.getOne(transaction.getId());
        tx.setDate(transaction.getDate());
        tx.setTotal(transaction.getTotal());
        tx.setNote(transaction.getNote());
        tx.setCategory(transaction.getCategory());
        if(transaction.getType()==null){
            tx.setType("Debit");
        }
        else {
            tx.setType(transaction.getType());
        }
       transactionService.saveOne(tx);


        return "redirect:/budgets/"+tx.getBudget().getId();
    }





}
