package com.example.start.controller;

import com.example.start.dto.request.user.PermissionRequest;
import com.example.start.dto.response.ApiResponse;
import com.example.start.dto.response.user.PermissionResponse;
import com.example.start.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor // Khởi tạo các private final attributes trong constructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionResponse> Create(@RequestBody PermissionRequest permissionRequest){
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.Create(permissionRequest))
                .code(200)
                .build();
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>> GetAll(){
        ApiResponse<List<PermissionResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(permissionService.GetAll());
        return apiResponse;
//        return ApiResponse.<List<PermissionResponse>>builder()
//                .result(permissionService.GetAll())
//                .build();
    }
}
