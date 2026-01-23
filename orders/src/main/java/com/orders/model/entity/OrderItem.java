package com.orders.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orders.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_items")
public class OrderItem extends BaseEntity<Long> {
    private Integer quantity;
    private Double priceAtTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    private Long productId;
}
