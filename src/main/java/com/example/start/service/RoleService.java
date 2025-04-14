package com.example.start.service;

import com.example.start.dto.request.user.RoleRequest;
import com.example.start.dto.response.user.RoleResponse;
import com.example.start.entity.Permission;
import com.example.start.entity.Role;
import com.example.start.exception.AppException;
import com.example.start.exception.ErrorCode;
import com.example.start.mapper.RoleMapper;
import com.example.start.repository.PermissionRepository;
import com.example.start.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleMapper roleMapper;
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public RoleResponse CreateRole(RoleRequest request) {
        var role = roleMapper.toRole(request);

        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    //    @PreAuthorize("hasAuthority('approve')")
    @PreAuthorize("hasRole('ADMIN')")
    public List<RoleResponse> GetRole() {
        List<RoleResponse> roleResponses = new ArrayList<>();
        for (var r : roleRepository.findAll()
        ) {
            roleResponses.add(roleMapper.toRoleResponse(r));
        }
        return roleResponses;
    }

//    @PreAuthorize("hasRole('ADMIN')")
    public RoleResponse UpdateRole(String roleName, RoleRequest roleRequest) {
        Role role = roleRepository.findByName(roleName).orElseThrow(
                ()-> new AppException(ErrorCode.ROLE_NOT_EXIST));
        role.setName(roleRequest.getName());

        Set<Permission> permissions = permissionRepository.findAllById(roleRequest.getPermissions())
                .stream().collect(Collectors.toSet());
        var availablePermissions = role.getPermissions();
        permissions.addAll(availablePermissions);
        role.setPermissions(permissions);
        roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }
}
