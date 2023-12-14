package com.store.repository;

import com.store.entity.FavouriteItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteItemRepository extends JpaRepository<FavouriteItem, Long> {
    List<FavouriteItem> findAllByUserId(String userId);
    void deleteFavouriteItemByProductIdAndUserId(Long productId, String userId);
    boolean existsByProductIdAndUserId(Long productId, String userId);

}