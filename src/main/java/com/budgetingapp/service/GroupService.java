package com.budgetingapp.service;

import com.budgetingapp.domain.Budget;
import com.budgetingapp.domain.Group;
import com.budgetingapp.repositories.BudgetRepository;
import com.budgetingapp.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private BudgetService budgetService;


    public Group createGroup (Long budgetId) {

        Group group = new Group();
        Budget budget = budgetService.findOne(budgetId);
        budget.getGroups().add(group);
        group.setBudget(budget);

        return groupRepository.save(group);
    }

    public Group getGroup (Long groupId) {

       return groupRepository.getOne(groupId);
    }

    public Group saveByGroup (Group group) {

        return  groupRepository.save(group);
    }
}
