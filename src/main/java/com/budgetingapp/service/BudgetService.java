package com.budgetingapp.service;

import com.budgetingapp.domain.Budget;
import com.budgetingapp.domain.Group;
import com.budgetingapp.domain.User;
import com.budgetingapp.repositories.BudgetRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepo;

    public TreeSet<Budget> getBudgets(@AuthenticationPrincipal User user) {
        Set<User> users = new HashSet<>();
        users.add(user);

        return budgetRepo.findAllByUsersIn(users);
    }

    public Budget createNewBudget(User user, Budget budget, String total) {
        Set<User> users = new HashSet<>();
        Set<Budget> budgets = new HashSet<>();

        users.add(user);

        budgets.add(budget);

        long count = getBudgetCount(users);

        budget.setName(LocalDate.now().getMonth().toString()+" Budget #" + ++count);
        budget.setUsers(users);

        LocalDate firstOfMonth = LocalDate.now().withDayOfMonth(1);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate lastOfMonth = LocalDate.now().withDayOfMonth(firstOfMonth.lengthOfMonth());
        lastOfMonth.format(dtf);

        budget.setStartDate(firstOfMonth);
        budget.setEndDate(lastOfMonth);

        user.setBudgets(budgets);

        budget.setTotalPlanned(Double.parseDouble(total));

        return budgetRepo.save(budget);
    }

    public void update (Budget budget) {

        budgetRepo.save(budget);
    }

    private long getBudgetCount(Set<User> users) {
        return budgetRepo.countAllByUsersIn(users);
    }

    public Budget findOne(Long budgetId) {

        Optional<Budget> budget = budgetRepo.findById(budgetId);

        return budget.orElseThrow();
    }

    public LocalDate convertStringToDate(String date) throws ParseException
    {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

}