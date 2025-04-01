package com.example.society.Mapper;
import com.example.society.DTO.Request.UserDTO;
import com.example.society.DTO.Response.UserResponse;
import com.example.society.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ConverterMapper.class)
public interface UserMapper {
    @Mapping(source = "address", target = "address", qualifiedByName = "stringToAddress")
    @Mapping(source = "hometown", target = "hometown", qualifiedByName = "stringToAddress")
    User toUser (UserDTO userDTO);

    @Mapping(source = "userID", target = "userID", qualifiedByName = "objectIDToString")
    @Mapping(source = "address", target = "address", qualifiedByName = "addressToString")
    @Mapping(source = "hometown", target = "hometown", qualifiedByName = "addressToString")
    UserResponse toUserResponseDTO (User user);
}


