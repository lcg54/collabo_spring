package com.coffee.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderProductListResponse {
    private Long productId;
    private String productName;
    private int quantity;
}
