package com.devsenior.pablo.service;

import java.util.ArrayList;
import java.util.List;

import com.devsenior.pablo.exception.UserNotFoundException;
import com.devsenior.pablo.model.User;

public class UserService {
    private final List<User> users;

    public UserService() {
        users = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void deleteUser(User user) {
        users.remove(user);
    }

    public List<User> getUsers() {
        return users;
    }

    public User getUserbyId(String id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User not found whit id: " + id));
    }

    public User getUserbyName(String name) {
        return users.stream()
                .filter(u -> u.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User not found whit name: " + name));
    }
}
