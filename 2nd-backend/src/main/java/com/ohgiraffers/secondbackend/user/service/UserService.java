package com.ohgiraffers.secondbackend.user.service;

import com.ohgiraffers.secondbackend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


}
