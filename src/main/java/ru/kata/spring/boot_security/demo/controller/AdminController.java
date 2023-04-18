package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;


    @GetMapping("/table")
    public String usersList(ModelMap model) {
        model.addAttribute("users", userService.getListOfUsers());
        model.addAttribute("new-user", new UserDto());
        model.addAttribute("user", new UserDto());
        model.addAttribute("rolesList", roleService.getListOfRoles());
        return "admin_panel_final";
    }

    @GetMapping("/add")
    public String showFormForAddUser(ModelMap model) {
        UserDto userDto = new UserDto();
        model.addAttribute("rolesList", roleService.getListOfRoles());
        model.addAttribute("user", userDto);
        return "add";
    }

    @PostMapping(value = "/add")
    public String addUser(@ModelAttribute("user") UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userService.add(userDto);
        return "redirect:/admin/table";
    }


    @GetMapping("/update")
    public String showFormForUpdateUser(ModelMap model, @RequestParam(name = "id") Long id) {
        UserDto userDto = userService.findById(id);
        List<String> rolesList = roleService.getListOfRolesAsString();
        model.addAttribute("rolesList", rolesList);
        model.addAttribute("user", userDto);
        return "update";
    }

    @PostMapping(value = "/update")
    public String updateUser(@ModelAttribute("user") UserDto userDto) {
        UserDto userDtoForMerge = new UserDto();
        userDtoForMerge.setId(userDto.getId());
        userDtoForMerge.setName(userDto.getName());
        userDtoForMerge.setSurname(userDto.getSurname());
        userDtoForMerge.setUsername(userDto.getUsername());
        userDtoForMerge.setAge(userDto.getAge());
        userDtoForMerge.setPassword(userDto.getPassword());
        userDtoForMerge.setRoles(userDto.getRoles());
        System.out.println(userDto.getUsername());
        System.out.println(userDto.getName());
        System.out.println(userDto.getSurname());
        System.out.println(userDto.getAge());
        System.out.println(userDto.getChosenRoles());
        userService.merge(userDtoForMerge);
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
