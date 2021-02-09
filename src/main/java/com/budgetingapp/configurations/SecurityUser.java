package com.budgetingapp.configurations;

import com.budgetingapp.domain.Authority;
import com.budgetingapp.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Set;

public class SecurityUser extends User implements UserDetails {


    public SecurityUser() {
    }

    public SecurityUser(User user) {
        this.setAuthority(user.getAuthority());
        this.setBudgets(user.getBudgets());
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
    }

    @Override
    public Set<Authority> getAuthorities() {
        return super.getAuthority();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
