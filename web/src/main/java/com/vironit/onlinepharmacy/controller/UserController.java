package com.vironit.onlinepharmacy.controller;

import com.vironit.onlinepharmacy.dto.UserData;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.service.user.UserService;
import org.springframework.web.bind.annotation.*;

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
    public long add(@RequestBody UserData userData) {
        return userService.add(userData);
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {
        return userService.get(id);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody UserData userData, @PathVariable Long id) {
        userService.update(userData);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        userService.remove(id);
    }
}
