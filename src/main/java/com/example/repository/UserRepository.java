package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.UserModel;


public interface UserRepository extends JpaRepository<UserModel,Long> {

    boolean existsByEmail(String email);
    UserModel findByEmail(String email);
    void deleteByEmail(String email);
}
