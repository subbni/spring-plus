package org.example.expert;

import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.user.entity.ProfileImage;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class TestData {

    // Unique IDs
    public static final Long ID_1 = 1L;
    public static final Long ID_2 = 2L;
    public static final Long ID_3 = 3L;


    // User Data
    public static final String EMAIL_1 = "testUser1@example.com";
    public static final String NICKNAME_1 = "testUser1";
    public static User getTestUser(Long id, String email, String nickname, UserRole userRole) {
        return User.fromAuthUser(new AuthUser(id, email, nickname, userRole));
    }

    // Profile Data
    public static final String STORED_FILENAME_1 = "storedFileName1";
    public static final String STORED_FILE_URL_1 = "https://storedFileUrl1";
    public static final ProfileImage PROFILE_IMAGE_1 = new ProfileImage(STORED_FILENAME_1, STORED_FILE_URL_1);

    // MultipartFile Data
    public static final MultipartFile MOCK_MULTIPART_FILE_1 = new MockMultipartFile(
            "profile_image",
            "test.jpg",
            "image/jpeg",
            "dummy image data".getBytes()
    );
}


