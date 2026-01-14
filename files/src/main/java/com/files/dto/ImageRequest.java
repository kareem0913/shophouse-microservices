package com.files.dto;

import com.files.validation.FileConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Data
public class ImageRequest {
    @NotBlank
    private String subFolder;
    private Set<@FileConstraint MultipartFile> images;
}
