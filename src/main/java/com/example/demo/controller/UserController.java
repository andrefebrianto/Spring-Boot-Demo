package com.example.demo.controller;

import com.example.demo.common.constant.PageConstant;
import com.example.demo.model.entity.User;
import com.example.demo.model.mapper.UserMapper;
import com.example.demo.model.request.UserCreateRequest;
import com.example.demo.model.request.UserUpdateRequest;
import com.example.demo.model.response.UserResponse;
import com.example.demo.model.response._MessageSuccessResponse;
import com.example.demo.model.response._PageResponse;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = "authorize")
public class UserController {

    @Autowired private UserMapper userMapper;

    @Autowired private UserService userService;

    @GetMapping("/")
    public ResponseEntity<_PageResponse<UserResponse>> getAll(
            @And({
                        @Spec(path = "firstName", spec = LikeIgnoreCase.class),
                        @Spec(path = "lastName", spec = LikeIgnoreCase.class),
                        @Spec(path = "email", spec = LikeIgnoreCase.class),
                        @Spec(path = "isDeleted", spec = Equal.class)
                    })
                    Specification<User> filter,
            @RequestParam(defaultValue = PageConstant.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = PageConstant.DEFAULT_SIZE) int size) {

        Page<User> pageItems = userService.findAll(filter, page, size);
        Page<UserResponse> responseItems = pageItems.map(userMapper::userToUserResponse);

        return new ResponseEntity<>(new _PageResponse<>(responseItems), HttpStatus.OK);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<_MessageSuccessResponse> delete(@PathVariable long id) {
        userService.deleteById(id);
        return ResponseEntity.ok(new _MessageSuccessResponse("Delete successful"));
    }
}
