package com.example.start.mapper;

import com.example.start.dto.request.FileUrlRequest;
import com.example.start.dto.response.FileUrlResponse;
import com.example.start.entity.FileUrl;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileUrlMapper {
    FileUrl toFileUrl(FileUrlRequest fileUrlRequest);
    FileUrlResponse toFileUrlResponse(FileUrl fileUrl);
}
