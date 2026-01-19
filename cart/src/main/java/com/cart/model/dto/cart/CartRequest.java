package com.cart.model.dto.cart;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartRequest {
    @NotNull(message = "product id must not be null")
    private Long productId;
    private Integer quantity;
}
