package com.coffee.Entity;

import com.coffee.constant.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @Min(value = 100, message = "가격은 100원 이상이어야 합니다.")
    private int price;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    @Min(value = 10, message = "재고 수량은 10개 이상이어야 합니다.")
    @Max(value = 1000, message = "재고 수량은 1000개 이하이어야 합니다.")
    private int stock;

    @Column(nullable = false)
    @NotBlank(message = "이미지는 필수 입력 사항입니다.")
    private String image;

    @Column(nullable = false, length = 1000)
    @NotBlank(message = "상품 설명은 필수 입력 사항입니다.")
    private String description;

    private LocalDate regDate;

    @PrePersist
    protected void onCreate() {
        this.regDate = LocalDate.now();
    }
}
