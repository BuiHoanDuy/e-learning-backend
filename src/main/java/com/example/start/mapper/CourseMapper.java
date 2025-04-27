package com.example.start.mapper;

import com.example.start.dto.response.CourseResponse;
import com.example.start.entity.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseResponse toCourseResponse(Course course);
}
