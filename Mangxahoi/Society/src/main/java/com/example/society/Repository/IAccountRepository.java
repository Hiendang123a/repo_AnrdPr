package com.example.society.Repository;

import com.example.society.Entity.Account;
import com.example.society.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IAccountRepository extends MongoRepository<Account, ObjectId> {
    Optional<Account> findByUsername(String username);
    boolean existsByUsername(String username);
}
