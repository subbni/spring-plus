package org.example.expert.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.user.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping("/image")
    public ResponseEntity<Void> uploadProfileImage(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestParam("file") MultipartFile file
    ) {
        userProfileService.uploadProfileImage(authUser.getId(), file);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/image")
    public ResponseEntity<Void> deleteProfileImage(
            @AuthenticationPrincipal AuthUser authUser
    ) {
        userProfileService.deleteProfileImage(authUser.getId());
        return ResponseEntity.ok().build();
    }
}
