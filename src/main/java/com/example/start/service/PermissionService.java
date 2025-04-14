package com.example.start.service;

import com.example.start.dto.request.user.PermissionRequest;
import com.example.start.dto.response.user.PermissionResponse;
import com.example.start.entity.Permission;
import com.example.start.mapper.PermissionMapper;
import com.example.start.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionMapper permissionMapper;
    PermissionRepository permissionRepository;

    public PermissionResponse Create(PermissionRequest permissionRequest){
        Permission permission = permissionMapper.toPermission(permissionRequest);
        return permissionMapper.toPermissionResponse(
                permissionRepository.save(permission));
    }

    public List<PermissionResponse> GetAll(){
        return permissionRepository.findAll().stream().map(permissionMapper::toPermissionResponse).toList();
    }
}
