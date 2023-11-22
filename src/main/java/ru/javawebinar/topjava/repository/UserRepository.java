package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.ArrayList;
import java.util.List;

public interface UserRepository {
    // null if not found, when updated
    User save(User user);

    // false if not found
    boolean delete(int id);

    // null if not found
    User get(int id);

    // null if not found
    User getByEmail(String email);

    List<User> getAll();

    default User getWithMeals(int id) {
        throw new UnsupportedOperationException();
    }

    default List<Role> getUserRoles(int userId) {
        return new ArrayList<>();
    }

    default void saveUserRoles(int userId, List<Role> roles) {
    }

    default boolean deleteUserRole(int userId, Role role) {
        return false;
    }
}