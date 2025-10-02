package com.coffee.Controller;

import com.coffee.DTO.OrderListResponse;
import com.coffee.DTO.OrderRequest;
import com.coffee.DTO.OrderStatusUpdateRequest;
import com.coffee.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/list")
    public ResponseEntity<List<OrderListResponse>> getOrderList(@RequestParam Long memberId, @RequestParam String role) {
        List<OrderListResponse> orders = orderService.getOrderListByRole(memberId, role);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId, @RequestParam Long memberId, @RequestParam String role) {
        orderService.deleteOrder(orderId, memberId, role);
        return ResponseEntity.ok("주문이 삭제되었습니다.");
    }

    @PatchMapping("/update/{orderId}")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody OrderStatusUpdateRequest request,
            @RequestParam String role) {

        orderService.updateOrderStatus(orderId, request.getNewStatus(), role);
        return ResponseEntity.ok("주문 상태가 변경되었습니다.");
    }
}
