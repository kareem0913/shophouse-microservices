package com.products.service.admin.category;

import com.products.error.exception.DuplicateResourceException;
import com.products.error.exception.ResourceNotFoundException;
import com.products.mapper.CategoryMapper;
import com.products.model.dto.category.CategoryCreate;
import com.products.model.dto.category.CategoryResponse;
import com.products.model.entity.Category;
import com.products.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.products.util.Util.deleteFile;
import static com.products.util.Util.saveFile;

@Service
@Slf4j
//@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final Path uploadDir;

    public AdminCategoryServiceImpl(CategoryRepository categoryRepository,
                                    CategoryMapper categoryMapper,
                                    @Value("${app.properties.upload-path}") String dir
    ) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.uploadDir = Paths.get(dir).toAbsolutePath().normalize();
    }

    @Override
    public CategoryResponse findCategory(final Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> {
            log.error("Category with ID {} not found", id);
            return new ResourceNotFoundException("Category not found",
                    "No category found with the provided ID: " + id);
        });
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public Page<CategoryResponse> findAllCategories(Pageable pageable) {
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        return categoryPage.map(categoryMapper::toCategoryResponse);
    }

    @Override
    public CategoryResponse createCategory(final CategoryCreate categoryCreate) {
        String name = categoryCreate.getName();
        if(existsByName(name)){
            log.error("Category with name '{}' already exists", name);
            throw new DuplicateResourceException(
                    "Category already exists",
                    "A category with the name '" + name + "' already exists."
            );
        }

        Category category = categoryMapper.toCategory(categoryCreate);

        MultipartFile img = categoryCreate.getImage();
        if(!img.isEmpty()){
            String stored = saveFile(img, uploadDir, "categories");
            category.setImageUrl(stored);
        }

        Category createdCategory = categoryRepository.save(category);
        log.info("Category '{}' created successfully", categoryCreate.getName());
        return categoryMapper.toCategoryResponse(createdCategory);
    }

    @Override
    public CategoryResponse updateCategory(final Long id, final CategoryCreate categoryCreate) {
        Category existingCategory = categoryRepository.findById(id).orElseThrow(() -> {
            log.error ("Category with ID {} not found", id);
            return new ResourceNotFoundException("Category not found",
                    "No category found with the provided ID: " + id);
        });

        String updatedName = categoryCreate.getName();
        if (existsByName(updatedName) && !existingCategory.getName().equals(updatedName)) {
            log.error("Category with name {} already exists", updatedName);
            throw new DuplicateResourceException("Category with name '" + updatedName + "' already exists.",
                    "A category with the same name already exists in the database.");
        }

        existingCategory.setName(updatedName);
        existingCategory.setParentId(categoryCreate.getParentId());
        existingCategory.setPosition(categoryCreate.getPosition());
        existingCategory.setStatus(categoryCreate.getIsActive());

        MultipartFile image = categoryCreate.getImage();
        if(!image.isEmpty()){
            deleteFile(uploadDir, existingCategory.getImageUrl());
            String stored = saveFile(image, uploadDir, "categories");
            existingCategory.setImageUrl(stored);
        }

        Category updatedCategory = categoryRepository.save(existingCategory);
        log.info("Category with ID {} updated successfully", id);
        return categoryMapper.toCategoryResponse(updatedCategory);

    }

    @Override
    public Page<CategoryResponse> findCategoriesByParentId(Long parentId, Pageable pageable) {
        Page<Category> categoryPage = categoryRepository.findAllByParentId(parentId, pageable);
        return categoryPage.map(categoryMapper::toCategoryResponse);
    }

    @Transactional
    @Override
    public void changeCategoryStatus(Long id, boolean status) {
        if (!categoryRepository.existsById(id)) {
            log.error("Category with ID {} not found", id);
            throw new ResourceNotFoundException("Category not found",
                    "No category found with the provided ID: " + id);
        }

        categoryRepository.updateStatusById(id, status);
        log.info("Category with ID {} status changed to {}", id, status ? "active" : "inactive");
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> {
            log.error("Category with ID {} not found", id);
            return new ResourceNotFoundException("Category not found",
                    "No category found with the provided ID: " + id);
        });

        if (category.getImageUrl() != null) {
            deleteFile(uploadDir, category.getImageUrl());
        }

        categoryRepository.delete(category);
        log.info("Category with ID {} deleted successfully", id);
    }

    private boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }
}
