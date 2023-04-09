package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;

    @Autowired
    public UserServiceImp(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    @Transactional
    public void add(UserDto userDto) {
        User user = new User(userDto);
        if (user.getName().equals("Николай")) {
            user.setRoles(roleDao.listRoles());
        } else {
            user.setRoles(roleDao.listRoles().stream().filter(i -> Objects.equals(i.getName(), "ROLE_USER")).collect(Collectors.toList()));
        }
 //       user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.add(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getListOfUsers() {
        return userDao.listUsers().stream().map(UserDto::new).collect(Collectors.toList());
    }

    @Override
    public UserDto findById(Long id) {
        return new UserDto(userDao.findById(id));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userDao.remove(userDao.findById(id));
    }

    @Override
    @Transactional
    public void merge(UserDto userDto) {
        User user = userDao.findById(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setAge(userDto.getAge());
        user.setPassword(userDto.getPassword());
        userDao.merge(user);
    }
    @Override
    public UserDto findByUsername(String username) {
        return new UserDto(userDao.findByUsername(username));
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getRoles());
    }
}
