package com.coffee.DTO;

import com.coffee.constant.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderListResponse {
    private Long orderId;
    private LocalDate orderDate;
    private OrderStatus orderStatus;
    private List<OrderProductListResponse> orderItems;
}
