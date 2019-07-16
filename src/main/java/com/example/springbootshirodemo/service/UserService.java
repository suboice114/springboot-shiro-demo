package com.example.springbootshirodemo.service;

import com.example.springbootshirodemo.model.User;

public interface UserService {
    User findByUsername(String username);

}
