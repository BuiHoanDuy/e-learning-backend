package com.example.start.controller;

import com.example.start.dto.request.user.RoleRequest;
import com.example.start.dto.response.ApiResponse;
import com.example.start.dto.response.user.RoleResponse;
import com.example.start.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor // Khởi tạo các private final attributes trong constructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {
    RoleService roleService;

    @PostMapping("/single")
    ApiResponse<RoleResponse> CreateRole(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.CreateRole(request))
                .code(200)
                .build();
    }

    @GetMapping("")
    ApiResponse<List<RoleResponse>> GetRole() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.GetRole())
                .code(200)
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<RoleResponse> UpdateRole(@PathVariable String name, @RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.UpdateRole(name, request))
                .code(200)
                .build();
    }
}
