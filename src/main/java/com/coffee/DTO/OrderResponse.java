package com.coffee.DTO;

import com.coffee.constant.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponse {
    private Long memberId;
    private OrderStatus orderStatus;
    private List<OrderProductResponse> orderItems;
}
