package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/users")
public class AdminController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;



    @GetMapping()
    public List<UserDto> usersList() {
        return userService.getListOfUsers();
    }

    @PostMapping()
    public void addUser(@RequestBody UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userService.add(userDto);
    }


    @PutMapping()
    public void updateUser(@RequestBody UserDto userDto) {
        userService.merge(userDto);
    }


    @GetMapping("/user")
    public UserDto showLoggedInUserInfo(Authentication authentication) {
        return userService.findByUsername(authentication.getName());
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(name = "id") Long id) {
        userService.deleteById(id);
    }

    @Autowired
    public AdminController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }
}
