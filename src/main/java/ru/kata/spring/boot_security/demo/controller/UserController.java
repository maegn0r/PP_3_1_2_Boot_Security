package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.service.UserService;



@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping()
    public String showLoggedInUserInfo(ModelMap model, Authentication authentication) {
        UserDto userDto = userService.findByUsername(authentication.getName());
        model.addAttribute("user", userDto);
        return "user";
    }

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
}
