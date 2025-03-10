package com.example.recruit2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.recruit2.models.user;

public interface userRepository extends JpaRepository<user,Integer>{
    Optional<user> findByLogin(String login);
}
