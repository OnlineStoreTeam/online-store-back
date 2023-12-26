package com.store.repository;

import com.store.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select count(p) from Product p where p.category.id =:id")
    long countItemsByCategory(Long id);

    @Query("select new com.store.entity.Category(c.id, c.name, c.title, c.path, count(i.id)) from Category c left join Product i " +
            "on c.id = i.category.id group by c.id")
    Page<Category> findAll(Pageable pageable);
}