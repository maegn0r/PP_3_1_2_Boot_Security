package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.service.UserService;


@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping()
    public UserDto showLoggedInUserInfo(Authentication authentication) {
        return userService.findByUsername(authentication.getName());
    }

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
}
