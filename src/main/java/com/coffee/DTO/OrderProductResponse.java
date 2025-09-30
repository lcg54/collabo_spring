package com.coffee.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderProductResponse {
    private Long cartProductId;
    private Long productId;
    private int quantity;
}
