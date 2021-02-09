package com.budgetingapp.repositories;

import com.budgetingapp.domain.Budget;
import com.budgetingapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.TreeSet;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long>
{
    TreeSet<Budget> findAllByUsersIn(Set<User> users);

    long countAllByUsersIn(Set<User> users);
}