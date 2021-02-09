package com.budgetingapp.controllers;

import com.budgetingapp.domain.Group;
import com.budgetingapp.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/budgets/{budgetId}/groups")
public class GroupController {


    @Autowired
    private GroupService groupService;


    @PostMapping("")
    public String addGroup (@PathVariable Long budgetId) {

        Group newGroup = groupService.createGroup(budgetId);

        return "redirect:/budgets/"+budgetId+"/groups/"+newGroup.getId();
    }

    @GetMapping("{groupId}")
    public String getGroup (@PathVariable Long groupId, Model model) {

        Group group = groupService.getGroup(groupId);
        model.addAttribute("group",group);

        return "group";
    }

    @PostMapping("{groupId}")
    public String postGroup (@PathVariable Long groupId, @PathVariable Long budgetId, @ModelAttribute Group groupName, Model model ) {

        Group group = groupService.getGroup(groupId);
        group.setName(groupName.getName());
        groupService.saveByGroup(group);


        return "redirect:/budgets/"+ budgetId;
    }

}
