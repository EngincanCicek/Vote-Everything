package com.example.voteeverything.dao;

import com.example.voteeverything.model.User;

import java.util.List;

public interface UserDAO {
    void insert(User user);
    void update(User user);
    void delete(User user);
    User getUserById(int userId);
    List<User> getAllUsers();
}
