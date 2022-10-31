package com.example.demo.model.mapper;

import com.example.demo.model.entity.User;
import com.example.demo.model.request.AuthSignUpRequest;
import com.example.demo.model.request.UserCreateRequest;
import com.example.demo.model.request.UserUpdateRequest;
import com.example.demo.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper instance = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "firstName", source = "firstName")
    User authSignUpRequestToUser(AuthSignUpRequest source);

    @Mapping(target = "firstName", source = "firstName")
    User userCreateRequestToUser(UserCreateRequest source);

    @Mapping(target = "firstName", source = "firstName")
    User userUpdateRequestToUser(UserUpdateRequest source);

    @Mapping(target = "firstName", source = "firstName")
    UserResponse userToUserResponse(User source);
}
