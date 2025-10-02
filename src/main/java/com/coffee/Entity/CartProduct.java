package com.coffee.Entity;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @RequiredArgsConstructor @AllArgsConstructor @ToString
@Entity
@Table(name="cart_products")
public class CartProduct {
    @Id
    @Column(name = "cart_product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 카트상품 다수에 카트 하나 대응
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)  // 카트상품에 다수에 상품 하나 대응
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private int quantity; // 구매 수량
}
