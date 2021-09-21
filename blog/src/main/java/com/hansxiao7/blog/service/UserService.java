package com.hansxiao7.blog.service;

import com.hansxiao7.blog.po.User;
import org.springframework.stereotype.Service;

public interface UserService {
    User checkUser(String username, String password);
    User findByUsernameAndEmail(String username, String email);
}
