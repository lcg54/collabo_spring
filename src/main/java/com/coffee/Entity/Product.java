package com.coffee.Entity;

import com.coffee.constant.Category;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false, length = 1000)
    private String description;

    private LocalDate regDate;

    @PrePersist
    protected void onCreate() {
        this.regDate = LocalDate.now();
    }
}
