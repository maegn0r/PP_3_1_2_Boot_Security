package ru.kata.spring.boot_security.demo.dto;

import ru.kata.spring.boot_security.demo.model.User;

import javax.validation.constraints.*;
import java.util.Set;

public class UserDto {
    private Long id;
    @NotBlank
    @Size(min = 4, max = 50)
    private String username;

    private String name;
    private String surname;
    @NotBlank
    @Size(min = 4, max = 50)
    private String password;

    private Byte age;
    private Set<Long> chosenRoles;

    public UserDto(String username, String password, String name, String surname, Byte age, Long id, Set<Long> chosenRoles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.chosenRoles = chosenRoles;
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
