package org.example.expert.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.image.consts.FilePath;
import org.example.expert.domain.image.provider.ImageStorageProvider;
import org.example.expert.domain.user.entity.ProfileImage;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserRepository userRepository;
    private final ImageStorageProvider imageStorageProvider;


    @Transactional
    public void uploadProfileImage(Long userId, MultipartFile file) {
        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidRequestException("User not found"));
        if(user.getProfileImage() != null) {
            throw new InvalidRequestException("Profile image already exists");
        }

        user.updateProfileImage(uploadProfileImage(file));
    }

    @Transactional
    public void deleteProfileImage(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidRequestException("User not found"));
        if(user.getProfileImage() == null) {
            throw new InvalidRequestException("Profile image does not exist");
        }

        imageStorageProvider.deleteFile(user.getProfileImage().getStoredFileName());
        user.deleteProfileImage();
    }

    public ProfileImage uploadProfileImage(MultipartFile file) {
        String uniquePrefix = UUID.randomUUID() + "_";
        String storedFileName = FilePath.PROFILE_FILE_PATH + uniquePrefix + file.getOriginalFilename();
        String storedFileUrl = imageStorageProvider.uploadFile(
                file,
                FilePath.PROFILE_FILE_PATH + uniquePrefix
        );

        return ProfileImage.builder()
                .storedFileName(storedFileName)
                .storedFileUrl(storedFileUrl)
                .build();
    }
}
