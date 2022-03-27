package com.example.restapi1.Repository;

import com.example.restapi1.Entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    boolean existsByEmail(String email);
}
