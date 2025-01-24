package com.example.society.dao.Interface;

import com.example.society.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO   {
    User getUser(Long userID);
    void createUser(User user);
    void updateUser(User user);
    void deleteUser(Long userID);
    User findByUsername(String username);
    boolean existsByUsername(String username);
    //List<User> searchUsers(String name, Date dob, String gender, Address currentAddress, Address hometown);

}
