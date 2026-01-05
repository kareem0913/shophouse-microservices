package com.products.model.dto.category;

import com.products.validation.FileConstraint;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CategoryCreate {

    @NotBlank(message = "{validation.category.name.not-blank}")
    @Size(min = 2, max = 50, message = "{validation.category.name.size}")
    private String name;

    @PositiveOrZero(message = "{validation.category.parent-id.positive-or-zero}")
    private Long parentId;

    @Min(value = 0, message = "{validation.category.position.min}")
    @Max(value = 10, message = "{validation.category.position.max}")
    private Integer position;
    private Boolean isActive = true;

    @FileConstraint
    private MultipartFile image;
}
