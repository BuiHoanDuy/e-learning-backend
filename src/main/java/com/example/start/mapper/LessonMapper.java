package com.example.start.mapper;

import com.example.start.dto.request.LessonRequest;
import com.example.start.dto.response.LessonResponse;
import com.example.start.entity.Lesson;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LessonMapper {
    Lesson toLesson(LessonRequest request);
    LessonResponse toLessonResponse(Lesson lesson);
}
