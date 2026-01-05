package com.products.controller.admin;

import com.products.model.dto.category.CategoryCreate;
import com.products.model.dto.category.CategoryResponse;
import com.products.service.admin.category.AdminCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Admin - Category Management", description = "Admin operations for managing categories")
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;

    @GetMapping
    @Operation(summary = "Get all categories (Admin)", description = "Retrieve a paginated list of all categories - Admin access required")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required")
    })
    public Page<CategoryResponse> httpGetAllCategories(
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return adminCategoryService.findAllCategories(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID (Admin)", description = "Retrieve a specific category by its ID - Admin access required")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public CategoryResponse httpGetCategoryById(
            @Parameter(description = "Category ID", required = true, example = "1")
            @NotNull @PathVariable Long id) {
        return adminCategoryService.findCategory(id);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    @Operation(summary = "Create category (Admin)", description = "Create a new category with image upload - Admin access required")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required")
    })
    public CategoryResponse httpCreateCategory(
            @Parameter(description = "Category data with image", required = true)
            @Valid @ModelAttribute @NotNull CategoryCreate categoryCreate) {
        return adminCategoryService.createCategory(categoryCreate);
    }

    @PutMapping(consumes = {"multipart/form-data"}, value = "/{id}")
    @Operation(summary = "Update category (Admin)", description = "Update an existing category - Admin access required")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public CategoryResponse httpUpdateCategory(
            @Parameter(description = "Category ID", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Updated category data", required = true)
            @Valid @ModelAttribute @NotNull CategoryCreate categoryCreate) {
        return adminCategoryService.updateCategory(id, categoryCreate);
    }

    @GetMapping("/parent/{parentId}")
    @Operation(summary = "Get categories by parent ID (Admin)", description = "Retrieve categories under a specific parent category - Admin access required")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required"),
            @ApiResponse(responseCode = "404", description = "Parent category not found")
    })
    public Page<CategoryResponse> httpGetCategoriesByParentId(
            @Parameter(description = "Parent category ID", required = true, example = "1")
            @PathVariable Long parentId,
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return adminCategoryService.findCategoriesByParentId(parentId, pageable);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Change category status (Admin)", description = "Enable or disable a category - Admin access required")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category status updated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public void httpChangeCategoryStatus(
            @Parameter(description = "Category ID", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "New status (true = active, false = inactive)", required = true, example = "true")
            @RequestParam boolean status) {
        adminCategoryService.changeCategoryStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category (Admin)", description = "Delete a category - Admin access required")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public void httpDeleteCategory(
            @Parameter(description = "Category ID", required = true, example = "1")
            @PathVariable Long id) {
        adminCategoryService.deleteCategory(id);
    }
}