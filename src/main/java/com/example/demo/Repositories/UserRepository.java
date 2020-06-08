package com.example.demo.Repositories;

import com.example.demo.Domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

        User findByEmail(String email);
        User findById(int id);
        User findByPassword(String password);
}
