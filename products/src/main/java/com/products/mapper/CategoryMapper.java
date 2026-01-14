package com.products.mapper;

import com.products.config.AppProperties;
import com.products.model.dto.category.CategoryCreate;
import com.products.model.dto.category.CategoryResponse;
import com.products.model.entity.Category;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class CategoryMapper {

    @Autowired
    protected AppProperties appProperties;

    @Mapping(source = "status", target = "isActive")
    public abstract CategoryResponse toCategoryResponse(Category category);

    @Mapping(source = "isActive", target = "status")
    @Mapping(target = "imageUrl", ignore = true)
    public abstract Category toCategory(CategoryCreate categoryCreate);

    @InheritConfiguration(name = "toCategory")
    public abstract Category toCategory(CategoryResponse categoryResponse);

    @AfterMapping
    protected void buildFullImageUrl(Category category, @MappingTarget CategoryResponse dto) {
        if (category.getImageUrl() != null) {
            dto.setImageUrl(appProperties.getFilesUrl() + category.getImageUrl());
        }
    }
}
