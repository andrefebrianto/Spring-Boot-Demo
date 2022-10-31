package com.example.demo.repository;

import com.example.demo.model.entity.Role;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.UserRole;

public interface UserRoleRepository extends _BaseEntityRepository<UserRole, UserRole.UserRoleId> {

    UserRole findByUserAndRole(User user, Role role);
}
