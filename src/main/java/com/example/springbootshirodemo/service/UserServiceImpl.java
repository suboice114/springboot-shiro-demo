package com.example.springbootshirodemo.service;

import com.example.springbootshirodemo.mapper.UserMapper;
import com.example.springbootshirodemo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {


        return userMapper.selectUserByName(username).get(0);
    }
}
