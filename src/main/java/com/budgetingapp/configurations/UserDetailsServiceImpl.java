package com.budgetingapp.configurations;

import com.budgetingapp.domain.User;
import com.budgetingapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

       User optional = userRepository.findByUsername(s);
        System.out.println(optional);
       if(optional == null){
           throw new UsernameNotFoundException("User does not exist");
       }

        return new SecurityUser(optional);
    }
}
