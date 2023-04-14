package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDao;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;


    @Override
    public List<Role> getListOfRoles() {
        return roleDao.findAll();
    }

    @Override
    public Role findRoleById(Long id) {
        return roleDao.findById(id).orElseThrow(()-> new RuntimeException("Роли с таким ID нет в базе данных"));
    }

    @Override
    public Role findRoleByName(String name) {
        return roleDao.findByName(name).orElseThrow(()-> new RuntimeException("Роли с таким названием нет в базе данных"));
    }

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
}
