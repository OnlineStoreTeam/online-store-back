package com.store.repository;

import com.store.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select count(p) from Product p where p.category.id =:id")
    long countItemsByCategory(Long id);
}