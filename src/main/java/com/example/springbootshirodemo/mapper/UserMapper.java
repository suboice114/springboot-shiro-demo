package com.example.springbootshirodemo.mapper;

import com.example.springbootshirodemo.model.User;

import java.util.List;

public interface UserMapper {
    List<User> selectUserByName(String username);
}
