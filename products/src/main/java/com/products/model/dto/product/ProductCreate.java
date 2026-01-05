package com.products.model.dto.product;

import com.products.model.enums.DiscountType;
import com.products.validation.FileConstraint;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Data
public class ProductCreate {

    @NotBlank(message = "Product name cannot be blank")
    @Size(min = 2, max = 50, message = "Product name must be between 2 and 50 characters")
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @Positive(message = "Price must be greater than 0")
    private double price;

    @PositiveOrZero(message = "Discount must be zero or a positive value")
    private double discount;

    @NotNull(message = "Discount type is required")
    private DiscountType discountType;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    private boolean status = true;

    @NotEmpty(message = "At least one category id is required")
    private Set<Long> categoriesIds;

    private Set<@FileConstraint MultipartFile> images;
}