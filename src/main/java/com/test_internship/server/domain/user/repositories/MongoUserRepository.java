package com.test_internship.server.domain.user.repositories;

import com.test_internship.server.domain.user.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoUserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
}
