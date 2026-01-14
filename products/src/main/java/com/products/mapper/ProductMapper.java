package com.products.mapper;

import com.products.config.AppProperties;
import com.products.model.dto.product.ProductCreate;
import com.products.model.dto.product.ProductResponse;
import com.products.model.entity.Product;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public abstract class ProductMapper {

    @Autowired
    protected AppProperties appProperties;

    @Mapping(target = "imagesUrls", ignore = true)
    public abstract ProductResponse toProductResponse(Product product);

    @Mapping(target = "imagesUrls", ignore = true)
    @Mapping(target = "categories", ignore = true)
    public abstract Product toProduct(ProductCreate productCreate);

    @AfterMapping
    protected void setImageUrls(Product product, @MappingTarget ProductResponse productResponse) {
        if (product.getImagesUrls() != null) {
            productResponse.setImagesUrls(product.getImagesUrls().stream()
                    .map(image -> appProperties.getFilesUrl() + image.getImageUrl())
                    .collect(Collectors.toSet()));
        }
    }
}
