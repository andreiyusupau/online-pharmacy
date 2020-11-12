package com.vironit.onlinepharmacy.security;

import com.vironit.onlinepharmacy.dao.UserDao;
import com.vironit.onlinepharmacy.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BasicUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    public BasicUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found."));
        String email = user.getEmail();
        String password = user.getPassword();
        String role = user.getRole()
                .name();
        Set<GrantedAuthority> grantedAuthorities = Set.of(new SimpleGrantedAuthority("ROLE_" + role));
        return new org.springframework.security.core.userdetails.User(email, password, grantedAuthorities);
    }
}
