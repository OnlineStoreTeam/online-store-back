package com.store.repository;

import com.store.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    Order findOrderByUserIdAndNumber(String userId, String number);

    boolean existsByNumberAndUserId(String number, String userId);

    Page<Order> findAllByUserId(String userId, Pageable pageable);
}
