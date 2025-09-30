package com.coffee.DTO;

import com.coffee.Entity.CartProduct;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartProductResponse {
    private Long id;         // CartProduct의 PK (장바구니 항목 ID)
    private Long productId;    // Product의 PK (상품 ID)
    private String productName;  // Product의 ...
    private String productImage;
    private int price;
    private int quantity;

    public static CartProductResponse fromEntity(CartProduct item) {
        return new CartProductResponse(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getProduct().getImage(),
                item.getProduct().getPrice(),
                item.getQuantity()
        );
    }
}
