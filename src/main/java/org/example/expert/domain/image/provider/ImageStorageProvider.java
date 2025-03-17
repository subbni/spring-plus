package org.example.expert.domain.image.provider;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageProvider {

    String uploadFile(MultipartFile file, String path);
    void deleteFile(String fileName);
}
