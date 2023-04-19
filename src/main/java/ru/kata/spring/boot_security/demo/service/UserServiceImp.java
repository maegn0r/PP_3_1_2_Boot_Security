package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    private final UserDao userDao;
    private final RoleService roleService;

    @Autowired
    public UserServiceImp(UserDao userDao, RoleService roleService) {
        this.userDao = userDao;
        this.roleService = roleService;
    }

    @Override
    @Transactional
    public void add(UserDto userDto) {
        if (userDao.findByUsername(userDto.getUsername()).isPresent()) {
            throw new UnsupportedOperationException("Пользователь с таким именем уже существует!");
        }
        User user = new User(userDto);
        user.setRoles(userDto.getRoles().stream().map(i -> roleService.findRoleByName("ROLE_" + i)).collect(Collectors.toSet()));
        userDao.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getListOfUsers() {
        return userDao.findAll().stream().map(UserDto::new).collect(Collectors.toList());
    }


    @Override
    public UserDto findById(Long id) {
        return new UserDto(userDao.findById(id).orElseThrow(() -> new RuntimeException("Пользователя с таким ID нет в базе данных")));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

    @Override
    @Transactional
    public void merge(UserDto userDto) {
        User user = userDao.findById(userDto.getId()).orElseThrow(() -> new RuntimeException("Пользователя с таким ID нет в базе данных"));
        if (!(user.getUsername().equals(userDto.getUsername())) && userDao.findByUsername(userDto.getUsername()).isPresent()) {
            throw new UnsupportedOperationException("Пользователь с таким именем уже существует!");
        }
        user.setUsername(user.getUsername());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setAge(userDto.getAge());
        user.setRoles(userDto.getRoles().stream().map(i -> roleService.findRoleByName("ROLE_" + i)).collect(Collectors.toSet()));
        userDao.save(user);
    }

    @Override
    public UserDto findByUsername(String username) {
        return new UserDto(userDao.findByUsername(username).orElseThrow(() -> new RuntimeException("Пользователя с таким именем нет в базе данных")));
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username).orElseThrow(() -> new RuntimeException("Пользователя с таким именем нет в базе данных"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getRoles());
    }
}
