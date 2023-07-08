package com.store.repository;

import com.store.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAdminRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByPrice(int i, Pageable secondPageWithFiveElements);
}
