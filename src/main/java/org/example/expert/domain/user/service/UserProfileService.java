package org.example.expert.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.image.consts.FilePath;
import org.example.expert.domain.image.dto.ImageUploadResult;
import org.example.expert.domain.image.provider.ImageStorageProvider;
import org.example.expert.domain.user.dto.response.UserProfileResponse;
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

    @Transactional(readOnly = true)
    public UserProfileResponse getUserProfile(Long userId) {
        User user = getUserById(userId);

        return new UserProfileResponse(
                user.getId(),user.getEmail(),
                user.getNickname(),
                user.getProfileImageUrl() // NPE 방지
        );
    }

    @Transactional
    public void uploadProfileImage(Long userId, MultipartFile file) {
        User user = getUserById(userId);
        if(user.getProfileImage() != null) {
            throw new InvalidRequestException("Profile image already exists");
        }

        ImageUploadResult uploadResult = imageStorageProvider.uploadFile(file, FilePath.PROFILE_FILE_PATH);
        user.updateProfileImage(new ProfileImage(uploadResult.getStoredFileName(), uploadResult.getStoredFileUrl()));
    }

    @Transactional
    public void deleteProfileImage(Long userId) {
        User user = getUserById(userId);
        if(user.getProfileImage() == null) {
            throw new InvalidRequestException("Profile image does not exist");
        }

        imageStorageProvider.deleteFile(user.getProfileImage().getStoredFileName());
        user.deleteProfileImage();
    }

    private User getUserById(Long userId) {
        return userRepository.findByIdWithProfileImage(userId)
                .orElseThrow(() -> new InvalidRequestException("User not found"));
    }
}
