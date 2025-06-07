package com.zch1001.pi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping
public class ImgController {

    private static final String UPLOAD_DIR = "/root/uploads/";

    @PostMapping("/upload_img")
    public ResponseEntity<?> uploadImage(@RequestParam("img") MultipartFile file) {
        try {
            // 验证文件是否为空
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }

            // 验证文件类型
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.badRequest().body("Only image files are allowed");
            }

            // 创建保存目录
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // 生成唯一文件名
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            String fileName = UUID.randomUUID().toString() + fileExtension;

            // 保存文件
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(file.getInputStream(), filePath);

            // 返回上传后的文件 URL
            String fileUrl = "http://116.205.181.81:8081/" + fileName;
            return ResponseEntity.ok().body(new UploadResponse(fileUrl));
        } catch (IOException e) {
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        }
    }

    // 响应类
    private class UploadResponse {
        private final String url;

        public UploadResponse(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }
}
