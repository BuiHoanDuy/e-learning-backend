package com.example.start.mapper;

import com.example.start.dto.request.AssignmentRequest;
import com.example.start.dto.response.AssignmentResponse;
import com.example.start.entity.Assignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.lang.annotation.Target;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {
    Assignment toAssignment(AssignmentRequest assignmentRequest);

    AssignmentResponse toAssignmentResponse(Assignment assignment);

    Assignment updateAssignmentFromRequest(AssignmentRequest assignmentRequest, @MappingTarget Assignment assignment);
}
