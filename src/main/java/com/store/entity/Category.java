package com.store.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull

    @Pattern(regexp = "^[A-Za-z0-9!@#$%^&*() ]+$", message = "Name should only contain alphanumeric characters")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters long")
    @Column(unique = true)
    private String name;

    @Pattern(regexp = "^[A-Za-z0-9 ]+$", message = "Title should only contain alphanumeric characters")
    @Size(min = 2, max = 50, message = "Title should be between 2 and 50 characters long")
    private String title;

    @Pattern(regexp = "^[A-Za-z0-9!@#$%^&*() ]+$", message = "Article should only contain alphanumeric characters")

    @Pattern(regexp = "^[A-Za-z0-9!@#$%^&*() .]+$", message = "Name should only contain alphanumeric characters")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 50 characters long")
    @Column(unique = true)
    private String name;

    @Pattern(regexp = "^[A-Za-z0-9 .]+$", message = "Title should only contain alphanumeric characters")
    @Size(min = 2, max = 100, message = "Title should be between 2 and 50 characters long")
    private String title;

    @Pattern(regexp = "^[A-Za-z0-9!@#$%^&*() .]+$", message = "Article should only contain alphanumeric characters")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters long")
    private String path;

    @Transient
    private long productCount;

    public Category(Long id, String name, String title, String path, long productCount) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.path = path;
        this.productCount = productCount;
    }
}