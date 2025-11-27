package com.example.projetmpisi.service;

import com.example.projetmpisi.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    User saveUser(User user);

    List<User> getAllUsers();

    Optional<User> getUserById(int id);

    User updateUser(int id, User user);

    void deleteUser(int id);
}

