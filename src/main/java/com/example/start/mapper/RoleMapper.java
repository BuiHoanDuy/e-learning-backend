package com.example.start.mapper;

import com.example.start.dto.request.user.RoleRequest;
import com.example.start.dto.response.user.RoleResponse;
import com.example.start.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
