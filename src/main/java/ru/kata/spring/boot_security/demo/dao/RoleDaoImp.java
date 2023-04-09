package ru.kata.spring.boot_security.demo.dao;


import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImp implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Role role) {
        entityManager.persist(role);
    }

    @Override
    public Role findByName(String name) {
        Role role = entityManager.find(Role.class, name);
        if (role == null) {
            throw new EntityNotFoundException("В базе нет роли, которая называется "
                    + name);
        }
        return role;
    }

    @Override
    public void remove(Role role) {
        entityManager.remove(entityManager.contains(role) ? role : entityManager.merge(role));
    }

    @Override
    public void merge(Role role) {
        entityManager.merge(role);
    }

    @Override
    public List<Role> listRoles() {
        return entityManager.createQuery("SELECT role from Role role", Role.class).getResultList();
    }
}
