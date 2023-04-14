//package ru.kata.spring.boot_security.demo.dao;
//
//import org.springframework.stereotype.Repository;
//import ru.kata.spring.boot_security.demo.model.User;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityNotFoundException;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//import java.util.List;
//
//
//@Repository
//public class UserDaoImp implements UserDao {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Override
//    public void add(User user) {
//        entityManager.persist(user);
//    }
//
//    @Override
//    public List<User> listUsers() {
//        return entityManager.createQuery("SELECT user from User user", User.class).getResultList();
//    }
//
//    @Override
//    public User findById(Long id) {
//        User user = entityManager.find(User.class, id);
//        if (user == null) {
//            throw new EntityNotFoundException("В базе нет пользователя с идентификатором "
//                    + id);
//        }
//        return user;
//    }
//
//    @Override
//    public User findByUsername(String username) {
//        User user = entityManager.createQuery("SELECT user from User user WHERE user.username = :username", User.class).setParameter("username",username).getSingleResult();
//        if (user == null) {
//            throw new EntityNotFoundException("В базе нет пользователя с именем "
//                    + username);
//        }
//        return user;
//    }
//
//    @Override
//    public Long checkUserCount(String username) {
//        Query query = entityManager.createNativeQuery("SELECT COUNT(*) FROM users WHERE username = :username").setParameter("username", username);
//        return ((Number) query.getSingleResult()).longValue();
//    }
//
//    @Override
//    public void merge(User user) {
//        entityManager.merge(user);
//    }
//
//    @Override
//    public void remove(User user) {
//        entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
//    }
//}
//
//
