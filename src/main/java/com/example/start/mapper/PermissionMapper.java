package com.example.start.mapper;

import com.example.start.dto.request.user.PermissionRequest;
import com.example.start.dto.response.user.PermissionResponse;
import com.example.start.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest permissionRequest);
    PermissionResponse toPermissionResponse (Permission permission);
    void updatePermission(@MappingTarget Permission permission, PermissionRequest permissionRequest);
}
