package com.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.login.model.Users;

public interface UserRepository extends JpaRepository<Users, String> {
    Users findByUsername(String username);
  
    
}

