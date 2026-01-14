package com.products.model.dto.file;

import com.products.validation.FileConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageRequest {
    @NotBlank
    private String subFolder;
    private Set<@FileConstraint MultipartFile> images;
}
