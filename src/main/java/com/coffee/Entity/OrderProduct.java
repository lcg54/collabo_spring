package com.coffee.Entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_products")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 주문상품 다수에 주문 하나 대응
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)  // 주문상품에 다수에 상품 하나 대응
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private int quantity; // 주문 수량
}
