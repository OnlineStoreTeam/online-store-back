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

    @Query(value = "SELECT * FROM products WHERE product_status <> 0 ORDER BY product_status ASC;",
            nativeQuery = true)
    Page<Product> findAllSort(Pageable pageable);

    @Query(value = "SELECT * FROM products WHERE category = ?1 AND product_status <> 0 ORDER BY product_status ASC;",
            nativeQuery = true)
    Page<Product> findProductsByCategory (Pageable pageable, String category);
}
