package com.hansxiao7.blog.service;

import com.hansxiao7.blog.dao.UserRepository;
import com.hansxiao7.blog.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password){
        User user = userRepository.findByUsernameAndPassword(username, password);
        return user;
    }

    @Override
    public User findByUsernameAndEmail(String username, String email) {
        return userRepository.findByNicknameAndEmail(username, email);
    }
}
