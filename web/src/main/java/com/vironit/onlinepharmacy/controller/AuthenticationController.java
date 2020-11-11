package com.vironit.onlinepharmacy.controller;

import com.vironit.onlinepharmacy.dto.UserDto;
import com.vironit.onlinepharmacy.dto.UserLoginDto;
import com.vironit.onlinepharmacy.vo.UserPublicVo;
import com.vironit.onlinepharmacy.service.authentication.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final AuthenticationService<UserDto, UserPublicVo, UserLoginDto> authenticationService;

    public AuthenticationController(AuthenticationService<UserDto, UserPublicVo, UserLoginDto> authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public UserPublicVo login(@RequestBody @Valid UserLoginDto userLoginDto) {
        return authenticationService.login(userLoginDto);
    }

    @PostMapping("/register")
    public long register(@RequestBody @Valid UserDto userDto) {
        return authenticationService.register(userDto);
    }

}