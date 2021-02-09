package com.budgetingapp.service;

import com.budgetingapp.domain.Authority;
import com.budgetingapp.domain.User;
import com.budgetingapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepo;

    public User saveUser(User user)
    {
        Authority authority = new Authority();
        authority.setAuthority("ROLE_USER");
        authority.setUser(user);

        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority);

        user.setAuthority(authorities);
        user = userRepo.save(user);

        return user;
    }
}