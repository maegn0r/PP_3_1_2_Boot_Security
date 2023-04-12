package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleDao {
    void add(Role role);

    Role findById(Long id);

    Role findByName(String name);

    void remove(Role role);

    void merge(Role role);

    List<Role> listRoles();
}
