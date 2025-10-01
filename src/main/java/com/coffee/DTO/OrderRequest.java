package com.coffee.DTO;

import com.coffee.constant.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    @NotNull
    private Long memberId;

    @NotNull
    private OrderStatus orderStatus;

    @NotNull
    private List<OrderProductRequest> orderItems;
}

