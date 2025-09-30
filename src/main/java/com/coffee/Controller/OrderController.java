package com.coffee.Controller;

import com.coffee.DTO.OrderRequest;
import com.coffee.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> order(@RequestBody OrderRequest dto) {
        orderService.createOrder(dto);
        return ResponseEntity.status(HttpStatus.OK).body("주문이 완료되었습니다!");
    }
}
