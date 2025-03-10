package com.example.recruit2.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.recruit2.repository.userRepository;



import com.example.recruit2.models.user;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final userRepository userRepository;

    public UserDetailsServiceImpl(userRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        user user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        return User.withUsername(user.getLogin())
                .password(user.getPassword()) // Используем роль из БД
                .build();
    }
}
