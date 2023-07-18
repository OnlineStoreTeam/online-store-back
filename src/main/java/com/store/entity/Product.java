package com.store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "products")
public class Product {
    private static final String SEQ_NAME="product_seq";

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;
    @Column(unique = true)
    private String article;
    private String name;
    private BigDecimal price;
    private String category;
    private String description;
    private int quantity;
    @Column(name = "product_status")
    private ProductStatus productStatus;
    @Column(name = "image_path")
    private String imagePath;
    @Transient
    private MultipartFile imageFile;
}
