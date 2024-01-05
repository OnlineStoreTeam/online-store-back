package com.store.service;

import com.store.dto.productDTOs.ProductCreateDTO;
import com.store.dto.productDTOs.ProductDTO;
import com.store.dto.productDTOs.ProductUpdateDTO;
import com.store.enums.ProductStatus;
import com.store.exception.DataNotFoundException;
import com.store.exception.InvalidDataException;
import com.store.mapper.ProductMapper;
import com.store.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    private final ProductMapper productMapper;


    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.findProductsByProductStatusIsNotOrderByProductStatus(ProductStatus.DELETED, pageable)
                .map(productMapper::toDto);
    }

    public ProductDTO getOneProductById(Long id) {
        return productMapper.toDto(productRepository.findById(id).
                orElseThrow(() -> new DataNotFoundException("There is no product with id" + id)));
    }

    public Page<ProductDTO> getProductsByCategoryId(Long categoryId, Pageable pageable) {
        return productRepository.findProductsByCategoryIdAndProductStatusIsNotOrderByProductStatus
                (categoryId, ProductStatus.DELETED, pageable).map(productMapper::toDto);
    }

    public ProductDTO addProduct(ProductCreateDTO productCreateDTO) {
        if (!productRepository.existsByCategoryId(productCreateDTO.getCategoryId())) {
            throw new DataNotFoundException("There is no category with id " + productCreateDTO.getCategoryId());
        }

        try {
            return productMapper.toDto(productRepository.save(productMapper.toEntity(productCreateDTO)));
        } catch (DataIntegrityViolationException e) {
            throw new InvalidDataException("Please, check for duplicate entries");
        }
    }

    public Page<ProductDTO> searchProducts(String name, Pageable pageable) {
        return productRepository.findProductByNameContainsIgnoreCase(name, pageable).map(productMapper::toDto);
    }

    public ProductDTO updateProduct(ProductUpdateDTO productUpdateDTO) {
        if (!productRepository.existsById(productUpdateDTO.getId())) {
            throw new DataNotFoundException("There is no product with id: " + productUpdateDTO.getId());
        }
        try {
            return productMapper.toDto(productRepository.save(productMapper.toEntity(productUpdateDTO)));
        } catch (DataIntegrityViolationException e) {
            throw new InvalidDataException("Please, check for duplicate entries");
        }
    }

    public void deleteProduct(Long id) {
        ProductDTO productDTO = productMapper.toDto(productRepository.findById(id).orElseThrow(() -> new DataNotFoundException("There is no product with id: " + id)));
        productDTO.setProductStatus(ProductStatus.DELETED);
        productRepository.save(productMapper.toEntity(productDTO));
    }
}