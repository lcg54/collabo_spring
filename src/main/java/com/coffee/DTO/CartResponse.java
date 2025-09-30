package com.coffee.DTO;

import com.coffee.Entity.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CartResponse {
    private Long cartId;
    private String memberName;
    private List<CartProductResponse> items;

    public static CartResponse fromEntity(Cart cart) {
        return new CartResponse(
                cart.getId(),
                cart.getMember().getName(),
                cart.getItems().stream()
                        .map(CartProductResponse::fromEntity)
                        .collect(Collectors.toList())
        );
    }
}
