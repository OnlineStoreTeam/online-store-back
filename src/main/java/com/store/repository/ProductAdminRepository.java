package com.store.repository;

import com.store.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAdminRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM products WHERE product_status <> 0;", nativeQuery = true)
    Page<Product> findAll(Pageable pageable);

    Page<Product> findProductsByCategoryContains(Pageable pageable, String category);
}
