package com.vironit.onlinepharmacy.security;

import com.vironit.onlinepharmacy.dto.UserDto;
import com.vironit.onlinepharmacy.dto.UserLoginDto;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.service.authentication.AuthenticationService;
import com.vironit.onlinepharmacy.vo.UserPublicVo;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Primary
public class BasicUserDetailsService implements UserDetailsService {

    private final AuthenticationService<UserDto, UserPublicVo, UserLoginDto> authenticationService;

    public BasicUserDetailsService(AuthenticationService<UserDto, UserPublicVo, UserLoginDto> authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authenticationService.getByEmail(username);
        String email = user.getEmail();
        String password = user.getPassword();
        String role = user.getRole()
                .name();
        Set<GrantedAuthority> grantedAuthorities = Set.of(new SimpleGrantedAuthority("ROLE_" + role));
        return new org.springframework.security.core.userdetails.User(email, password, grantedAuthorities);
    }
}
