package com.port.petfit.user.member.ImageUpload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageUploadService {

    @Value("${image.upload.directory}")
    private String uploadDir;

    public String saveImage(MultipartFile file) throws IOException {
        // 디렉토리가 없으면 생성
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        // 파일명 설정 (UUID를 사용하여 고유한 파일명 생성)
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        // 파일 경로 설정
        String filePath = uploadDir + "/" + fileName;
        // 파일 저장
        file.transferTo(new File(filePath));
        return fileName; // 저장된 파일명 반환 (혹은 전체 경로를 반환할 수도 있음)
    }
}