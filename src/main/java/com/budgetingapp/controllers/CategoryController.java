package com.budgetingapp.controllers;


import com.budgetingapp.domain.Category;
import com.budgetingapp.domain.Transaction;
import com.budgetingapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/budgets/{budgetId}/groups/{groupId}/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @PostMapping("")
    public String createCategory (@PathVariable Long groupId) {

        Category category = categoryService.createNewCategory(groupId);

        return "redirect:/budgets/"+ category.getGroupp().getBudget().getId()+"/groups/"+groupId+"/categories/"+category.getId();
    }

    @GetMapping("{categoryId}")
    public String getCategory (@PathVariable Long categoryId, Model model) {

        Category category = categoryService.findOne(categoryId);
        LocalDate start = category.getGroupp().getBudget().getStartDate();
        LocalDate end = category.getGroupp().getBudget().getEndDate();

        Set<Transaction> filteredTx = category.getTransactions().stream()
                .filter(t->t.getDate().isEqual(start)|t.getDate().isAfter(start)&&t.getDate().isEqual(end)|t.getDate().isBefore(end))
                .collect(Collectors.toSet());
        model.addAttribute("filteredTx",filteredTx);
        model.addAttribute("category",category);
        model.addAttribute("group",category.getGroupp());
        boolean hasTransactions = category.getTransactions().size()>0;
            model.addAttribute("hasTransactions", hasTransactions);

        return "category";
    }

    @PostMapping("{categoryId}")
    public String postCategory ( @ModelAttribute Category category) {

        Category categoryUpdate = categoryService.findOne(category.getId());
        categoryUpdate.setName(category.getName());
        categoryUpdate.setBudget(category.getBudget());
        categoryService.saveOne(categoryUpdate);

        return "redirect:/budgets/"+ categoryUpdate.getGroupp().getBudget().getId();
    }

}
