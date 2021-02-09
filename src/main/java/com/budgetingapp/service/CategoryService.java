package com.budgetingapp.service;

import com.budgetingapp.domain.Category;
import com.budgetingapp.domain.Group;
import com.budgetingapp.repositories.CategoryRepository;
import com.budgetingapp.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    public Category createNewCategory (Long groupId) {

        Category category = new Category();
        Group group = groupRepository.getOne(groupId);
        group.getCategories().add(category);
        category.setGroupp(group);

        return categoryRepository.save(category);

    }

    public Category findOne (Long categoryId){
        return categoryRepository.getOne(categoryId);
    }

    public Category saveOne (Category category) {

        return categoryRepository.save(category);
    }

}
