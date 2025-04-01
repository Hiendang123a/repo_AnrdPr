package com.example.society.Service.Impl;

import com.example.society.DTO.Response.UserResponse;
import com.example.society.Entity.User;
import com.example.society.Exception.AppException;
import com.example.society.Exception.ErrorCode;
import com.example.society.Mapper.UserMapper;
import com.example.society.Repository.IUserRepository;
import com.example.society.Service.Interface.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    IUserRepository userRepository;
    @Autowired
    UserMapper userMapper;
    @Override
    public UserResponse getUser(String userId) {
        User user = userRepository.findUserByUserID(new ObjectId(userId))
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponseDTO(user);
    }
}
