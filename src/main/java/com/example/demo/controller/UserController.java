package com.example.demo.controller;

import com.example.demo.common.constant.PageConstant;
import com.example.demo.model.entity.User;
import com.example.demo.model.mapper.UserMapper;
import com.example.demo.model.request.RoleRequest;
import com.example.demo.model.request.UserCreateRequest;
import com.example.demo.model.request.UserUpdateRequest;
import com.example.demo.model.response.UserResponse;
import com.example.demo.model.response._MessageSuccessResponse;
import com.example.demo.model.response._PageResponse;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "2. Users")
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = "authorize")
public class UserController {

    @Autowired private UserMapper userMapper;

    @Autowired private UserService userService;

    @GetMapping("/")
    @Operation(summary = "Get All")
    public ResponseEntity<_PageResponse<UserResponse>> getAll(
            @Parameter(example = PageConstant.SAMPLE_USER_FILTER)
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
    @Operation(summary = "Create")
    public ResponseEntity<UserResponse> create(@Validated @RequestBody UserCreateRequest request) {
        User user = userMapper.userCreateRequestToUser(request);
        user = userService.create(user);
        UserResponse response = userMapper.userToUserResponse(user);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get One")
    public ResponseEntity<UserResponse> get(@PathVariable long id) {
        User user = userService.findById(id);
        UserResponse response = userMapper.userToUserResponse(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update")
    public ResponseEntity<UserResponse> update(
            @Validated @RequestBody UserUpdateRequest request, @PathVariable long id) {
        User user = userMapper.userUpdateRequestToUser(request);
        user.setId(id);
        user = userService.update(user);
        UserResponse response = userMapper.userToUserResponse(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}/assign-role")
    @Operation(summary = "Assign Role")
    public ResponseEntity<UserResponse> assignRole(
            @PathVariable long id, @Validated @RequestBody RoleRequest request) {
        User user = userService.findById(id);
        user = userService.assignRole(user, request.getName());
        UserResponse response = userMapper.userToUserResponse(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}/unassign-role")
    @Operation(summary = "Unassign Role")
    public ResponseEntity<UserResponse> unassignRole(
            @PathVariable long id, @Validated @RequestBody RoleRequest request) {
        User user = userService.findById(id);
        user = userService.unassignRole(user, request.getName());
        UserResponse response = userMapper.userToUserResponse(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete")
    public ResponseEntity<_MessageSuccessResponse> delete(@PathVariable long id) {
        userService.deleteById(id);
        return ResponseEntity.ok(new _MessageSuccessResponse("Delete successful"));
    }
}
