package org.example.expert.domain.image.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.domain.common.exception.ServerException;
import org.example.expert.domain.image.dto.ImageUploadResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Provider implements ImageStorageProvider {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    @Override
    public ImageUploadResult uploadFile(MultipartFile file, String path) {
        String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());
        String storedFileName = path + uniqueFileName;

        try {
            uploadToS3(file, storedFileName);
            return new ImageUploadResult(storedFileName, getFileUrl(storedFileName));
        } catch (IOException e) {
            throw new ServerException("Failed to upload file.");
        } catch (S3Exception e) {
            log.error(e.awsErrorDetails().errorMessage());
            throw new ServerException("Failed to upload file.");
        }
    }

    private void uploadToS3(MultipartFile file, String fileName) throws IOException {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileName)
                        .metadata(metadata)
                        .contentType(file.getContentType())
                        .build(),
                RequestBody.fromBytes(file.getBytes())
        );
    }

    @Override
    public void deleteFile(String fileName) {
        try {
            s3Client.deleteObject(
                    DeleteObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .build()
            );
        } catch (S3Exception e) {
            log.error(e.awsErrorDetails().errorMessage());
            throw new ServerException("Failed to delete file.");
        }
    }

    private String generateUniqueFileName(String originalFilename) {
        if(!StringUtils.hasText(originalFilename)) {
            throw new IllegalArgumentException("File name cannot be null or empty.");
        }

        return UUID.randomUUID() + "_" + originalFilename;
    }

    private String getFileUrl(String fileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s",
                bucketName,
                region,
                fileName);
    }
}
