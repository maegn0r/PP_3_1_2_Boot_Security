package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.exceptions.EmailFormatException;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/users")
public class AdminController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final static String CHECK_EMAIL_FIELD = "(^[a-zA-Z0-9_.]+[@]{1}[a-z0-9]+[\\.][a-z]+$)";


    @GetMapping()
    @Secured("ROLE_ADMIN")
    public List<UserDto> usersList() {
        return userService.getListOfUsers();
    }

    @PostMapping()
    @Secured("ROLE_ADMIN")
    public ResponseEntity<HttpStatus> addUser(@RequestBody UserDto userDto) {
        if (!userDto.getUsername().matches(CHECK_EMAIL_FIELD)) {
            throw new EmailFormatException("Неправильный формат ввода email");
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userService.add(userDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public UserDto getUserById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @PutMapping()
    @Secured("ROLE_ADMIN")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody UserDto userDto) {
        if (!userDto.getUsername().matches(CHECK_EMAIL_FIELD)) {
            throw new EmailFormatException("Неправильный формат ввода email");
        }
        userService.merge(userDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @GetMapping("/user")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<UserDto> showLoggedInUserInfo(Authentication authentication) {
        return ResponseEntity.ok(userService.findByUsername(authentication.getName()));
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable(name = "id") Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Autowired
    public AdminController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }
}
