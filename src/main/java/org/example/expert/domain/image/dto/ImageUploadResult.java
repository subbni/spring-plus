package org.example.expert.domain.image.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ImageUploadResult {

    private final String storedFileName;
    private final String storedFileUrl;

    public ImageUploadResult(String storedFileName, String storedFileUrl) {
        this.storedFileName = storedFileName;
        this.storedFileUrl = storedFileUrl;
    }
}
