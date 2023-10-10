package com.store.service;

import com.store.dto.productDTOs.ProductCreateDTO;
import com.store.dto.productDTOs.ProductDTO;
import com.store.dto.productDTOs.ProductUpdateDTO;
import com.store.enums.ProductStatus;
import com.store.exception.DataNotFoundException;
import com.store.mapper.ProductMapper;
import com.store.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.findProductsByProductStatusIsNotOrderByProductStatus(ProductStatus.DELETED, pageable)
                .map(productMapper::toDto);
    }

    public List<ProductDTO> getProductsCategory(String category, Pageable pageable) {
        return productMapper.toDto(
                productRepository.findProductsByCategoryAndProductStatusIsNotOrderByProductStatus
                        (category, ProductStatus.DELETED, pageable)
        );
    }

    public ProductDTO addProduct(ProductCreateDTO productCreateDTO) {
        return productMapper.toDto(productRepository.save(productMapper.toEntity(productCreateDTO)));
    }

    public ProductDTO updateProduct(ProductUpdateDTO productUpdateDTO) {
        if (!productRepository.existsById(productUpdateDTO.getId())) {
            throw new DataNotFoundException("There is no product with id: " + productUpdateDTO.getId());
        }
        return productMapper.toDto(productRepository.save(productMapper.toEntity(productUpdateDTO)));
    }

    public void deleteProduct(Long id) {
        productRepository.findById(id).orElseThrow(() -> new DataNotFoundException("There is no product with id: " + id));
        ProductDTO productDTO = productMapper.toDto(productRepository.findById(id).orElse(null));
        productDTO.setProductStatus(ProductStatus.DELETED);
        productRepository.save(productMapper.toEntity(productDTO));
    }
}