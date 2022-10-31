package com.example.demo.model.mapper;

import com.example.demo.model.entity.Role;
import com.example.demo.model.request.RoleRequest;
import com.example.demo.model.response.RoleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleMapper instance = Mappers.getMapper(RoleMapper.class);

    @Mapping(target = "name", source = "name")
    Role roleRequestToRole(RoleRequest source);

    @Mapping(target = "name", source = "name")
    RoleResponse roleToRoleResponse(Role source);
}
