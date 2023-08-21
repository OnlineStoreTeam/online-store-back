package com.store.service.impl;

import com.store.dto.ProductAdminDto;
import com.store.entity.Product;
import com.store.entity.ProductStatus;
import com.store.repository.ProductAdminRepository;
import com.store.service.ProductAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;


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
                        .setProductStatus(productAdminDto.getProductStatus())
                        .setImagePath(productAdminDto.getImagePath()));
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
    public Page<ProductAdminDto> getActiveAndTemporarilyAbsentProducts(Pageable paging) {
        return productAdminRepository.findAll(paging).map(ProductAdminDto::fromEntity);
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
}
