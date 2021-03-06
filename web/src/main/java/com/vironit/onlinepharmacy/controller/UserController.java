package com.vironit.onlinepharmacy.controller;

import com.vironit.onlinepharmacy.dto.UserDto;
import com.vironit.onlinepharmacy.service.user.UserService;
import com.vironit.onlinepharmacy.vo.UserPublicVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Collection<UserPublicVo> getAll() {
        return userService.getAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MODERATOR','ADMIN')")
    public long add(@RequestBody  @Valid UserDto userDto) {
        return userService.add(userDto);
    }

    @GetMapping("/{id}")
    public UserPublicVo get(@PathVariable Long id) {
        return userService.get(id);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody  @Valid UserDto userDto, @PathVariable Long id) {
        userDto.setId(id);
        userService.update(userDto);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        userService.remove(id);
    }

}
