package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getListOfRoles();

    Role findRoleById(Long id);

    Role findRoleByName(String name);
    List<String> getListOfRolesAsListOfStrings();

}
