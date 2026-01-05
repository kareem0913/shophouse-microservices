package com.products.model.entity;

import com.products.base.BaseEntity;
import com.products.model.enums.DiscountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
@NamedEntityGraph(
        name = "Product.detail",
        attributeNodes = {
                @NamedAttributeNode("imagesUrls"),
                @NamedAttributeNode("categories")
        }
)
public class Product extends BaseEntity<Long> {

    private String name;
    private String description;
    private double price;
    private double discount;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;
    private Integer quantity;
    private boolean status;

    @Transient
    private double finalPrice;

    @Transient
    private double finalDiscount;

    @PostLoad
    public void calculateDiscountValues() {
        this.finalDiscount = calculateDiscount();
        this.finalPrice = this.price - this.finalDiscount;
    }

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> imagesUrls;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name= "product_categories",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    private Set<Category> categories;

    private double calculateDiscount() {
        if (discount <= 0 || discountType == null) return 0;

        return switch (discountType) {
            case PERCENTAGE -> Math.min((price * Math.min(discount, 100)) / 100, price);
            case AMOUNT -> Math.min(discount, price);
        };
    }

}
