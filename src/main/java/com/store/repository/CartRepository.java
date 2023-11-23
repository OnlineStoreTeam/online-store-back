package com.store.repository;

import com.store.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(String userId);

    List<Cart> findAllByUserId(String userId);

    Cart findCartByProductIdAndUserId(Long productId, String userId);

    void deleteCartByProductId(Long productId);

    boolean existsByProductIdAndUserId(Long productId, String userId);

    boolean existsAllByUserId(String userId);

    void deleteAllByUserId(String userId);
}
