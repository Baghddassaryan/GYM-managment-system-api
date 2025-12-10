package org.bagdev.gymmanagmentsystemapi.service;

import org.bagdev.gymmanagmentsystemapi.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUser(Long id);
    User createUser(User user);
}