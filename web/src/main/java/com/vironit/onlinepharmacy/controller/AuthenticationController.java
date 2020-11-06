package com.vironit.onlinepharmacy.controller;

import com.vironit.onlinepharmacy.dto.UserDto;
import com.vironit.onlinepharmacy.dto.UserLoginDto;
import com.vironit.onlinepharmacy.dto.UserPublicDto;
import com.vironit.onlinepharmacy.service.authentication.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final AuthenticationService<UserDto, UserPublicDto, UserLoginDto> authenticationService;

    public AuthenticationController(AuthenticationService<UserDto, UserPublicDto, UserLoginDto> authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public UserPublicDto login(@RequestBody UserLoginDto userLoginDto) {
        return authenticationService.login(userLoginDto);
    }

    @PostMapping("/register")
    public long register(@RequestBody UserDto userDto) {
        return authenticationService.register(userDto);
    }

}