package com.hansxiao7.blog.dao;

import com.hansxiao7.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(String username, String password);
    User findByNicknameAndEmail(String nickname, String email);
}
