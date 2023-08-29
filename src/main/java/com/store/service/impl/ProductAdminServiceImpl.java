package com.store.service.impl;

import com.store.dto.ProductAdminDto;
import com.store.entity.Product;
import com.store.entity.ProductStatus;
import com.store.repository.ProductRepository;
import com.store.service.ProductAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class ProductAdminServiceImpl implements ProductAdminService {

    private final ProductRepository productAdminRepository;

    @Override
    public ProductAdminDto addProduct(ProductAdminDto productAdminDto) {
        Product save = productAdminRepository.save(
                new Product()
                        .setArticle(productAdminDto.getArticle())
                        .setName(productAdminDto.getName())
                        .setPrice(productAdminDto.getPrice())
                        .setCategory(productAdminDto.getCategory())
                        .setDescription(productAdminDto.getDescription())
                        .setQuantity(productAdminDto.getQuantity())
                        .setProductStatus(productAdminDto.getProductStatus()));
        return mapProductToProductAdminDto(save);
    }

    public ProductAdminDto mapProductToProductAdminDto(Product product) {
        return new ProductAdminDto()
                .setId(product.getId())
                .setArticle(product.getArticle())
                .setName(product.getName())
                .setPrice(product.getPrice())
                .setCategory(product.getCategory())
                .setDescription(product.getDescription())
                .setQuantity(product.getQuantity())
                .setProductStatus(product.getProductStatus())
                .setImagePath(product.getImagePath());
    }

    @Override
    public ProductAdminDto saveImage(Long productId, MultipartFile imageFile) throws IOException {
        Product product = productAdminRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + productId));

        String imagePath = uploadImage(imageFile);
        product.setImagePath(imagePath);

        return mapProductToProductAdminDto(productAdminRepository.save(product));
    }

    public String uploadImage(MultipartFile imageFile) throws IOException {
        String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
        Path uploadDir = Paths.get("images");

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        try (InputStream inputStream = imageFile.getInputStream()) {
            Path filePath = uploadDir.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString();
        } catch (IOException e) {
            throw new IOException("Failed to save image", e);
        }
    }

    @Override
    public Page<ProductAdminDto> getActiveAndTemporarilyAbsentProducts(Pageable paging) {
        return productAdminRepository.findProductsByProductStatusIsNot(ProductStatus.DELETE, paging)
                .map(ProductAdminDto::fromEntity);
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productAdminRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + productId));
        productAdminRepository.save(product.setProductStatus(ProductStatus.DELETE));
    }

    @Override
    public ProductAdminDto updateProduct(Long productId, ProductAdminDto productAdminDto) {
        Product product = productAdminRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + productId));
        product.setArticle(productAdminDto.getArticle())
                .setName(productAdminDto.getName())
                .setPrice(productAdminDto.getPrice())
                .setCategory(productAdminDto.getCategory())
                .setDescription(productAdminDto.getDescription())
                .setQuantity(productAdminDto.getQuantity())
                .setProductStatus(productAdminDto.getProductStatus());
        if(productAdminDto.getQuantity() == 0){
            product.setProductStatus(ProductStatus.TEMPORARILY_ABSENT);
        }
        return mapProductToProductAdminDto(productAdminRepository.save(product));
    }

    @Override
    public ProductAdminDto updateImage(Long productId, MultipartFile imageFile) throws IOException {
        Product product = productAdminRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + productId));
        String imagePath = uploadImage(imageFile);
        product.setImagePath(imagePath);
        return mapProductToProductAdminDto(productAdminRepository.save(product));
    }
}
