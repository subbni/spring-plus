package org.example.expert.domain.user.service;

import org.example.expert.TestData;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.common.exception.ServerException;
import org.example.expert.domain.image.consts.FilePath;
import org.example.expert.domain.image.dto.ImageUploadResult;
import org.example.expert.domain.image.provider.ImageStorageProvider;
import org.example.expert.domain.user.dto.response.UserProfileResponse;
import org.example.expert.domain.user.entity.ProfileImage;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.example.expert.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private ImageStorageProvider imageStorageProvider;
    @InjectMocks
    private UserProfileService userProfileService;

    @Nested
    public class 사용자_프로필_조회시 {
        @Test
        public void 존재하지_않는_사용자는_예외발생() {
            // given
            Long invalidUserId = 99L;
            when(userRepository.findByIdWithProfileImage(anyLong())).thenReturn(Optional.empty());

            // when & then
            assertThrows(InvalidRequestException.class, () -> {
                userProfileService.getUserProfile(invalidUserId);
            });
        }

        @Test
        public void 프로필_이미지가_있으면_정상_반환() {
            // given
            User user = getTestUser(ID_1, EMAIL_1, NICKNAME_1, UserRole.ROLE_USER);
            user.updateProfileImage(PROFILE_IMAGE_1);
            when(userRepository.findByIdWithProfileImage(anyLong())).thenReturn(Optional.of(user));

            // when
            UserProfileResponse userProfileResponse = userProfileService.getUserProfile(user.getId());

            // then
            assertNotNull(userProfileResponse);
            assertEquals(STORED_FILE_URL_1, userProfileResponse.getProfileImageUrl());
        }

        @Test
        public void 프로필_이미지가_없으면_URL은_null() {
            // given
            User user = getTestUser(ID_1, EMAIL_1, NICKNAME_1, UserRole.ROLE_USER);
            when(userRepository.findByIdWithProfileImage(anyLong())).thenReturn(Optional.of(user));

            // when
            UserProfileResponse userProfileResponse = userProfileService.getUserProfile(user.getId());

            // then
            assertNotNull(userProfileResponse);
            assertNull(userProfileResponse.getProfileImageUrl());
        }
    }

    @Nested
    public class 사용자_프로필_이미지_업로드시 {
        @Test
        public void 존재하지_않는_사용자는_예외발생() {
            // given
            Long invalidUserId = 99L;
            when(userRepository.findByIdWithProfileImage(anyLong())).thenReturn(Optional.empty());

            MultipartFile file = MOCK_MULTIPART_FILE_1;

            // when & then
            assertThrows(InvalidRequestException.class, () -> {
                userProfileService.uploadProfileImage(invalidUserId, file);
            });
        }

        @Test
        public void 이미_프로필_이미지가_있으면_예외발생() {
            // given
            User user = getTestUser(ID_1, EMAIL_1, NICKNAME_1, UserRole.ROLE_USER);
            user.updateProfileImage(PROFILE_IMAGE_1);
            when(userRepository.findByIdWithProfileImage(anyLong())).thenReturn(Optional.of(user));

            MultipartFile file = MOCK_MULTIPART_FILE_1;

            // when & then
            InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> {
                userProfileService.uploadProfileImage(user.getId(), file);
            });
            assertEquals("Profile image already exists", exception.getMessage());
        }

        @Test
        public void 프로필_이미지_정상_업로드() {
            // given
            User user = getTestUser(ID_1, EMAIL_1, NICKNAME_1, UserRole.ROLE_USER);
            when(userRepository.findByIdWithProfileImage(anyLong())).thenReturn(Optional.of(user));

            MultipartFile file = MOCK_MULTIPART_FILE_1;
            ImageUploadResult imageUploadResult = new ImageUploadResult(STORED_FILENAME_1, STORED_FILE_URL_1);
            when(imageStorageProvider.uploadFile(file, FilePath.PROFILE_FILE_PATH)).thenReturn(imageUploadResult);

            // when
            userProfileService.uploadProfileImage(user.getId(), file);

            // then
            assertNotNull(user.getProfileImage());
            assertEquals(STORED_FILENAME_1, user.getProfileImage().getStoredFileName());
            assertEquals(STORED_FILE_URL_1, user.getProfileImage().getStoredFileUrl());
        }
    }

    @Nested
    public class 사용자_프로필_이미지_삭제시 {
        @Test
        public void 프로필_이미지가_없으면_예외발생() {
            // given
            User user = getTestUser(ID_1, EMAIL_1, NICKNAME_1, UserRole.ROLE_USER);
            when(userRepository.findByIdWithProfileImage(anyLong())).thenReturn(Optional.of(user));

            // when & then
            InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> {
                userProfileService.deleteProfileImage(user.getId());
            });
            assertEquals("Profile image does not exist", exception.getMessage());
        }

        @Test
        public void 스토리지_삭제_실패시_예외발생() {
            // given
            User user = getTestUser(ID_1, EMAIL_1, NICKNAME_1, UserRole.ROLE_USER);
            ProfileImage profileImage = new ProfileImage(STORED_FILENAME_1, STORED_FILE_URL_1);
            user.updateProfileImage(profileImage);
            when(userRepository.findByIdWithProfileImage(anyLong())).thenReturn(Optional.of(user));

            doThrow(new ServerException("Failed to delete file."))
                    .when(imageStorageProvider)
                    .deleteFile(anyString());

            // when & then
            assertThrows(ServerException.class, () -> userProfileService.deleteProfileImage(user.getId()));
            assertNotNull(user.getProfileImage());
        }

        @Test
        public void 프로필_이미지_정상_삭제() {
            // given
            User user = getTestUser(ID_1, EMAIL_1, NICKNAME_1, UserRole.ROLE_USER);
            ProfileImage profileImage = new ProfileImage(STORED_FILENAME_1, STORED_FILE_URL_1);
            user.updateProfileImage(profileImage);
            when(userRepository.findByIdWithProfileImage(anyLong())).thenReturn(Optional.of(user));

            doNothing().when(imageStorageProvider).deleteFile(anyString());

            // when
            userProfileService.deleteProfileImage(user.getId());

            // then
            assertNull(user.getProfileImage());
        }
    }
}