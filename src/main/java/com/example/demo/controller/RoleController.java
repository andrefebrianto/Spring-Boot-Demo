package com.example.demo.controller;

import com.example.demo.common.constant.PageConstant;
import com.example.demo.model.entity.Role;
import com.example.demo.model.mapper.RoleMapper;
import com.example.demo.model.request.RoleRequest;
import com.example.demo.model.response.RoleResponse;
import com.example.demo.model.response._MessageSuccessResponse;
import com.example.demo.model.response._PageResponse;
import com.example.demo.service.RoleService;
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
@Tag(name = "3. Roles")
@RequestMapping("/api/v1/roles")
@SecurityRequirement(name = "authorize")
public class RoleController {

    @Autowired private RoleMapper roleMapper;

    @Autowired private RoleService roleService;

    @GetMapping("/")
    @Operation(summary = "Get All")
    public ResponseEntity<_PageResponse<RoleResponse>> getAll(
            @Parameter(example = PageConstant.SAMPLE_ROLE_FILTER)
                    @And({
                        @Spec(path = "name", spec = LikeIgnoreCase.class),
                        @Spec(path = "isDeleted", spec = Equal.class)
                    })
                    Specification<Role> filter,
            @RequestParam(defaultValue = PageConstant.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = PageConstant.DEFAULT_SIZE) int size) {

        Page<Role> pageItems = roleService.findAll(filter, page, size);
        Page<RoleResponse> responseItems = pageItems.map(roleMapper::roleToRoleResponse);

        return new ResponseEntity<>(new _PageResponse<>(responseItems), HttpStatus.OK);
    }

    @PostMapping("/")
    @Operation(summary = "Create")
    public ResponseEntity<RoleResponse> create(@Validated @RequestBody RoleRequest request) {
        Role role = roleMapper.roleRequestToRole(request);
        role = roleService.create(role);
        RoleResponse response = roleMapper.roleToRoleResponse(role);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get One")
    public ResponseEntity<RoleResponse> get(@PathVariable long id) {
        Role role = roleService.findById(id);
        RoleResponse response = roleMapper.roleToRoleResponse(role);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update")
    public ResponseEntity<RoleResponse> update(
            @Validated @RequestBody RoleRequest request, @PathVariable long id) {
        Role role = roleMapper.roleRequestToRole(request);
        role.setId(id);
        role = roleService.update(role);
        RoleResponse response = roleMapper.roleToRoleResponse(role);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete")
    public ResponseEntity<_MessageSuccessResponse> delete(@PathVariable long id) {
        roleService.deleteById(id);
        return ResponseEntity.ok(new _MessageSuccessResponse("Delete successful"));
    }
}
