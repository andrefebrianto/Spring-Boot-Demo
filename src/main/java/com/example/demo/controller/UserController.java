package com.example.demo.controller;

import com.example.demo.model.entity.User;
import com.example.demo.model.mapper.UserMapper;
import com.example.demo.model.request.UserCreateRequest;
import com.example.demo.model.request.UserUpdateRequest;
import com.example.demo.model.response.UserResponse;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired private UserMapper userMapper;

    @Autowired private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserResponse> create(@Validated @RequestBody UserCreateRequest request) {
        User user = userMapper.userCreateRequestToUser(request);
        user = userService.create(user);
        UserResponse response = userMapper.userToUserResponse(user);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> get(@PathVariable long id) {
        User user = userService.findById(id);
        UserResponse response = userMapper.userToUserResponse(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(
            @Validated @RequestBody UserUpdateRequest request, @PathVariable long id) {
        User user = userMapper.userUpdateRequestToUser(request);
        user.setId(id);
        user = userService.update(user);
        UserResponse response = userMapper.userToUserResponse(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
