package com.example.start.controller;

import com.example.start.dto.request.authentication.IntrospectRequest;
import com.example.start.dto.request.authentication.LogoutRequest;
import com.example.start.dto.request.authentication.RefreshRequest;
import com.example.start.dto.response.ApiResponse;
import com.example.start.dto.request.authentication.AuthenticationRequest;
import com.example.start.dto.response.authentication.AuthenticationResponse;
import com.example.start.dto.response.authentication.IntrospectResponse;
import com.example.start.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor // Khởi tạo các private final attributes trong constructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;
    @PostMapping("/token")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        ApiResponse<AuthenticationResponse> authenticationResponseApiResponse = new ApiResponse<>();
        authenticationResponseApiResponse.setResult(authenticationService.authenticate(authenticationRequest));
        return authenticationResponseApiResponse;
    }
    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest introspectRequest) throws ParseException, JOSEException {
        ApiResponse<IntrospectResponse> introspectResponseApiResponse = new ApiResponse<>();
        introspectResponseApiResponse.setResult(authenticationService.introspect(introspectRequest));
        return introspectResponseApiResponse;
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request)
            throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder()
                .build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken (request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }
}
