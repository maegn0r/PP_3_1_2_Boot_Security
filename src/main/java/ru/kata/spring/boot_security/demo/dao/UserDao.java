package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    void add(User user);

    List<User> listUsers();

    User findById(Long id);

    User findByUsername(String username);

    Long checkUserCount(String username);

    void merge(User user);

    void remove(User user);
}