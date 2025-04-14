package com.example.start.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public CloudinaryService() {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dbanoyj2o",
                "api_key", "874847651639134",
                "api_secret", "u_57kaCRDNKt-5ilSe0wtG-F4Es"
        ));
    }

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        File file = convertToFile(multipartFile);
        try {
            // Sử dụng resource_type = auto để Cloudinary tự xác định loại file
            Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.asMap(
                    "resource_type", "auto", // Cho phép upload cả ảnh, video, pdf, v.v.
                    "type", "upload",
                    "access_mode", "public" // Đảm bảo file có thể truy cập công khai
            ));
            log.info(uploadResult.toString());
            return uploadResult.get("secure_url").toString();
        } finally {
            file.delete(); // Luôn xóa file tạm, kể cả khi lỗi xảy ra
        }
    }

    private File convertToFile(MultipartFile multipartFile) throws IOException {
        File convFile = File.createTempFile("upload-", "-" + multipartFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(multipartFile.getBytes());
        }
        return convFile;
    }
}

