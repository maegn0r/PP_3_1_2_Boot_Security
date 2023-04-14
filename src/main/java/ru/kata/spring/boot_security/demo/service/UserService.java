package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.dto.UserDto;

import java.util.List;

public interface UserService extends UserDetailsService {
    void add(UserDto userDto);

    List<UserDto> getListOfUsers();

    UserDto findById(Long id);

    void deleteById(Long id);

    void merge(UserDto userDto);

    UserDto findByUsername(String username);

}
