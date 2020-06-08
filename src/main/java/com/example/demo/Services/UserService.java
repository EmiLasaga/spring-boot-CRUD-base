package com.example.demo.Services;

import com.example.demo.Domain.User;
import com.example.demo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Boolean create(User user) {
        User u = userRepository.findByEmail(user.getEmail());
        if (u == null) {

            userRepository.save(user);
            return true;
        }
        return false;
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findById(int id) {
        return userRepository.findById(id);
    }

    public User findByPassword(String password) {
        return userRepository.findByPassword(password);
    }
}