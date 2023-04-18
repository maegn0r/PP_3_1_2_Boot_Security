package ru.kata.spring.boot_security.demo.dto;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.validation.constraints.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDto {
    private Long id;

    private String username;

    private String name;
    private String surname;

    private String password;

    private Byte age;
    private Set<Long> chosenRoles;

    private List<String> roles;

    public UserDto(String username, String password, String name, String surname, Byte age, Long id, Set<Long> chosenRoles, List<String> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.chosenRoles = chosenRoles;
        this.roles = roles;
    }

    public UserDto() {
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.age = user.getAge();
        this.roles = user.getRoleNames();
    }

    public List<String> getRoles() {
        return roles;
    }

    public String getRolesAsString() {
        return roles.stream().sorted().collect(Collectors.joining(" ")).replaceAll("ROLE_", "");
    }

    public List<String> getListOfStringNameOfRolesWithoutRole() {
        return roles.stream().sorted().map(i -> i.replaceAll("ROLE_", "")).collect(Collectors.toList());
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Set<Long> getChosenRoles() {
        return chosenRoles;
    }

    public void setChosenRoles(Set<Long> chosenRoles) {
        this.chosenRoles = chosenRoles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }
}
