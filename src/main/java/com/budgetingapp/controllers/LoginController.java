package com.budgetingapp.controllers;

import com.budgetingapp.domain.User;
import com.budgetingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;


    @RequestMapping(value="/login", method=RequestMethod.GET)
    public String getLogin (@ModelAttribute("user") User user)
    {

        return "login";
    }

    @RequestMapping(value="/register", method=RequestMethod.GET)
    public String getRegister (Model model)
    {
        User user = new User();
        model.addAttribute("user", user);

        return "register";
    }

    @RequestMapping(value="/register", method=RequestMethod.POST)
    public String postRegister (@ModelAttribute("user") User user, ModelMap model)
    {
        if (!StringUtils.isEmpty(user.getPassword()) && !StringUtils.isEmpty(user.getConfirmPassword()))
        {
            if (!user.getPassword().equals(user.getConfirmPassword()))
            {
                model.addAttribute("error", "Passwords don't match");
                return "register";
            }
        }

        if (StringUtils.isEmpty(user.getPassword()) || StringUtils.isEmpty(user.getConfirmPassword()))
        {
            model.addAttribute("error", "You must choose a password");
            return "register";
        }

        user = userService.saveUser(user);

        // dynamically logging in the user via Spring Security
        Authentication auth =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthority());

        SecurityContextHolder.getContext().setAuthentication(auth);

        return "redirect:/budgets";
    }

}
