package org.example.expert.domain.image.provider;

import org.example.expert.domain.image.dto.ImageUploadResult;
import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageProvider {

    ImageUploadResult uploadFile(MultipartFile file, String path);
    void deleteFile(String fileName);
}
