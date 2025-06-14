package com.example.start.mapper;

import com.example.start.dto.request.user.UserCreationRequest;
import com.example.start.dto.request.user.UserUpdateRequest;
import com.example.start.dto.response.user.UserResponse;
import com.example.start.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
