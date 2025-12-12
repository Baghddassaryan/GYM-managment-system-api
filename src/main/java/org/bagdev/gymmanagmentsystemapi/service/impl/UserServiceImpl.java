package org.bagdev.gymmanagmentsystemapi.service.impl;

import org.bagdev.gymmanagmentsystemapi.model.User;
import org.bagdev.gymmanagmentsystemapi.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final List<User> users = new ArrayList<>();

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public User getUser(Long id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User createUser(User user) {
        users.add(user);
        return user;
    }


}