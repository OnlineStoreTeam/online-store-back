package com.store.repository;

import com.store.entity.Product;
import com.store.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findProductsByProductStatusIsNot(ProductStatus productStatus, Pageable pageable);

    Page<Product> findProductsByProductStatusIsNotOrderByProductStatus(ProductStatus productStatus, Pageable pageable);

    Page<Product> findProductsByCategoryIdAndProductStatusIsNotOrderByProductStatus
            (Long categoryId, ProductStatus productStatus, Pageable pageable);
}
