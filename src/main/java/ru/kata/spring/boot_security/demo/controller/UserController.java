package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;


@Controller
@RequestMapping("/")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    private final RoleService roleService;

    @GetMapping(value = "/admin")
    public String usersList(ModelMap model) {
        model.addAttribute("users", userService.getListOfUsers());
        return "admin";
    }

    @GetMapping("/add")
    public String showFormForAddUser(ModelMap model){
        UserDto userDto = new UserDto();
        model.addAttribute("rolesList",roleService.getListOfRoles());
        model.addAttribute("user", userDto);
        return "add";

    }
    @PostMapping(value = "/add")
    public String addUser(@Valid @ModelAttribute ("user") UserDto userDto, BindingResult bindingResult,ModelMap model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("rolesList",roleService.getListOfRoles());
            return "add";
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userService.add(userDto);
        return "redirect:/admin";
    }


    @GetMapping("/update")
    public String showFormForUpdateUser(ModelMap model, @RequestParam(name = "id") Long id){
        UserDto userDto = userService.findById(id);
        model.addAttribute("rolesList",roleService.getListOfRoles());
        model.addAttribute("user", userDto);
        return "update";

    }

    @GetMapping("/user")
    public String showLoggedInUserInfo(ModelMap model, Authentication authentication){
        UserDto userDto = userService.findByUsername(authentication.getName());
        model.addAttribute("user", userDto);
        return "user";
    }

    @PostMapping(value = "/update")
    public String updateUser(@ModelAttribute("user") UserDto userDto){
        userService.merge(userDto);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete")
    public String deleteById(@RequestParam(name = "id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
    @Autowired
    public UserController(PasswordEncoder passwordEncoder, UserService userService, RoleService roleService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
    }
}
