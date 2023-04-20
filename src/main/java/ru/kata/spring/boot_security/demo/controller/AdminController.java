package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;


    @GetMapping("/table")
    public String usersList(ModelMap model) {
        model.addAttribute("users", userService.getListOfUsers());
        model.addAttribute("user", new UserDto());
        model.addAttribute("rolesList", roleService.getListOfRolesAsListOfStrings());
        return "admin-panel-final";
    }

    @PostMapping(value = "/add")
    public String addUser(@ModelAttribute("user") UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userService.add(userDto);
        return "redirect:/admin/table";
    }


    @PostMapping(value = "/update")
    public String updateUser(@ModelAttribute("user") UserDto userDto) {
        userService.merge(userDto);
        return "redirect:/admin/table";
    }


    @GetMapping("/user")
    public String showLoggedInUserInfo(ModelMap model, Authentication authentication) {
        UserDto userDto = userService.findByUsername(authentication.getName());
        model.addAttribute("user", userDto);
        return "user";
    }

    @GetMapping("/delete")
    public String deleteById(@RequestParam(name = "id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/table";
    }

    @Autowired
    public AdminController(PasswordEncoder passwordEncoder, UserService userService, RoleService roleService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
    }
}
