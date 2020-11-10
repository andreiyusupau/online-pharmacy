package com.vironit.onlinepharmacy.controller;

import com.vironit.onlinepharmacy.dto.UserDto;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.service.user.UserService;
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
    public Collection<User> getAll() {
        return userService.getAll();
    }

    @PostMapping
    public long add(@RequestBody  @Valid UserDto userDto) {
        return userService.add(userDto);
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {
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
