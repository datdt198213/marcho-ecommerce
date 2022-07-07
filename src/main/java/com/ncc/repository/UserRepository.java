package com.ncc.repository;

import com.ncc.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsernameOrPhoneOrEmail(String username, String phone, String email);
    User findByUsername(String username);
    Page<User> findByOrderByUsernameAsc(Pageable pageable);
    User findByEmail(String email);
}
