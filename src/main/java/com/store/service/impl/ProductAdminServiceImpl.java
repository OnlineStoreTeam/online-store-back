package com.store.service.impl;

import com.store.dto.ProductAdminDto;
import com.store.dto.ProductAdminDtoGet;
import com.store.entity.Product;
import com.store.repository.ProductAdminRepository;
import com.store.service.ProductAdminService;
import lombok.RequiredArgsConstructor;
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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAdminServiceImpl implements ProductAdminService {

    private final ProductAdminRepository productAdminRepository;

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
                .setProductStatus(product.getProductStatus());
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

    public List<ProductAdminDtoGet> getAllProducts() {
        List<Product> products = productAdminRepository.findAll();
        List<ProductAdminDtoGet> productAdminDtoList = new ArrayList<>();
        for (Product product: products){
            ProductAdminDtoGet productAdminDtoGet = new ProductAdminDtoGet().fromDto(product);
            productAdminDtoList.add(productAdminDtoGet);
        }
        return productAdminDtoList;
    }

    @Override
    public List<ProductAdminDtoGet> getActiveAndTemporarilyAbsentProducts() {
        List<ProductAdminDtoGet> products = getAllProducts();
        List<ProductAdminDtoGet> filteredProducts = new ArrayList<>();

        for (ProductAdminDtoGet product : products) {
            String status = String.valueOf(product.getProductStatus());
            if (status.equals("ACTIVE") || status.equals("TEMPORARILY_ABSENT")) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }
}
