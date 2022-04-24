package com.example.restapi1.repository;

import com.example.restapi1.business.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    boolean existsByEmail(String email);
}
