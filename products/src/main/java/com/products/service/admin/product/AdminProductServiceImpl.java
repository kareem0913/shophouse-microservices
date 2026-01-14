package com.products.service.admin.product;

import com.products.error.exception.ResourceNotFoundException;
import com.products.mapper.ProductMapper;
import com.products.model.dto.file.ImageRequest;
import com.products.model.dto.file.ImageResponse;
import com.products.model.dto.product.ProductCreate;
import com.products.model.dto.product.ProductResponse;
import com.products.model.entity.Category;
import com.products.model.entity.Product;
import com.products.model.entity.ProductImage;
import com.products.repository.CategoryRepository;
import com.products.repository.ProductRepository;
import com.products.service.client.FileFeignClient;
import com.products.validation.FileConstraint;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class AdminProductServiceImpl implements AdminProductService{

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FileFeignClient fileFeignClient;
    private static final String SUBFOLDER = "products";

    public AdminProductServiceImpl(ProductRepository productRepository,
                                   ProductMapper productMapper,
                                   CategoryRepository categoryRepository,
                                   FileFeignClient fileFeignClient
    ) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
        this.fileFeignClient = fileFeignClient;
    }

    @Override
    public ProductResponse findProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> {
            log.error("Product with ID {} not found", id);
            return new ResourceNotFoundException("Product not found",
                    "No product found with the provided ID: " + id);
        });
        return productMapper.toProductResponse(product);
    }

    @Override
    public Page<ProductResponse> findAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(productMapper::toProductResponse);
    }

    @Override
    public ProductResponse createProduct(final ProductCreate productCreate) {
        Product product = productMapper.toProduct(productCreate);
        Set<Category> categories = new HashSet<>(categoryRepository.findAllById(productCreate.getCategoriesIds()));
        product.setCategories(categories);

        List<ProductImage> images = saveProductImages(productCreate.getImages(), product);

        log.info("Built images set: size={}", images.size());
        product.setImagesUrls(images);

        Product savedProduct = productRepository.save(product);
        log.info("Product with ID {} created successfully", savedProduct.getId());
        return productMapper.toProductResponse(savedProduct);
    }

    @Transactional
    @Override
    public ProductResponse updateProduct(Long id, ProductCreate productCreate) {
        Product product = productRepository.findById(id).orElseThrow(() -> {
            log.error("Product with ID {} not found", id);
            return new ResourceNotFoundException("Product not found",
                    "No product found with the provided ID: " + id);
        });

        product.setName(productCreate.getName());
        product.setDescription(productCreate.getDescription());
        product.setPrice(productCreate.getPrice());
        product.setDiscount(productCreate.getDiscount());
        product.setDiscountType(productCreate.getDiscountType());
        product.setQuantity(productCreate.getQuantity());

        Set<Category> categories = new HashSet<>(categoryRepository.findAllById(productCreate.getCategoriesIds()));
        product.setCategories(categories);

        Set<@FileConstraint MultipartFile> images = productCreate.getImages();
        if (images != null && !images.isEmpty()) {
            log.info("Replacing product images...");

            List<ProductImage> existingImages = product.getImagesUrls();
            if (existingImages == null) {
                existingImages = new ArrayList<>();
                product.setImagesUrls(existingImages);
            }
            if (!existingImages.isEmpty()) {
                List<String> imagesUrls = extractImageUrls(product.getImagesUrls());
                fileFeignClient.deleteImagesApi(imagesUrls);
            }
            existingImages.clear();
            List<ProductImage> newImages = saveProductImages(images, product);
            existingImages.addAll(newImages);
        }

        Product updatedProduct = productRepository.save(product);

        return productMapper.toProductResponse(updatedProduct);
    }

    @Override
    public Page<ProductResponse> findProductsByCategoryId(Long categoryId, Pageable pageable) {
        Page<Product> products = productRepository.findByCategoriesId(categoryId, pageable);
        return products.map(productMapper::toProductResponse);
    }

    @Transactional
    @Override
    public void changeProductStatus(Long id, boolean status) {
        if(!productRepository.existsById(id) ) {
            log.error("Product with ID {} not found", id);
            throw new ResourceNotFoundException("Product not found",
                    "No product found with the provided ID: " + id);
        }
        productRepository.updateStatusById(id, status);
        log.info("Product with ID {} status changed to {}", id, status ? "active" : "inactive");
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> {
            log.error("Product with ID {} not found", id);
            return new ResourceNotFoundException("Product not found",
                    "No product found with the provided ID: " + id);
        });

        if (product.getImagesUrls() != null) {
            List<String> imagesUrls = extractImageUrls(product.getImagesUrls());
            fileFeignClient.deleteImagesApi(imagesUrls);
        }

        productRepository.delete(product);
        log.info("Product with ID {} deleted successfully", id);
    }

    @Override
    public Page<ProductResponse> searchProducts(String keyword, Pageable pageable) {
        return null;
    }

    private List<ProductImage> saveProductImages(Set<MultipartFile> productImages, Product product){
        ImageRequest imageRequest = new ImageRequest(SUBFOLDER, productImages);
        ImageResponse stored = fileFeignClient.saveImagesApi(imageRequest);
        @SuppressWarnings("unchecked")
        List<String> imageUrls = (List<String>) stored.data();
        return imageUrls
                .stream()
                .map(imageUrl -> {
                    ProductImage productImage = new ProductImage();
                    productImage.setImageUrl(imageUrl);
                    productImage.setProduct(product);
                    return productImage;
                })
                .toList();
    }

    private List<String> extractImageUrls(List<ProductImage> images){
        return images.stream().map(ProductImage::getImageUrl).toList();
    }
}
