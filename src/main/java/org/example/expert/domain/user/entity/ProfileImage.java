package org.example.expert.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "profile_images")
public class ProfileImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storedFileName;

    private String storedFileUrl;

    @Builder
    public ProfileImage(String storedFileName, String storedFileUrl) {
        this.storedFileName = storedFileName;
        this.storedFileUrl = storedFileUrl;
    }
}
