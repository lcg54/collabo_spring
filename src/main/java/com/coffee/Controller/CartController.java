package com.coffee.Controller;

import com.coffee.DTO.CartAddRequest;
import com.coffee.DTO.CartResponse;
import com.coffee.DTO.QuantityRequest;
import com.coffee.Service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<?> getCart(@RequestParam Long memberId) {
        CartResponse response = cartService.getCart(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/insert")
    public ResponseEntity<?> addItem(@RequestParam Long memberId, @RequestBody @Valid CartAddRequest dto) {
        cartService.addItem(memberId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("상품이 장바구니에 추가되었습니다.");
    }

    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<?> removeItem(@RequestParam Long memberId, @PathVariable Long itemId) {
        cartService.removeItem(memberId, itemId);
        return ResponseEntity.status(HttpStatus.OK).body("상품이 장바구니에서 삭제되었습니다.");
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<String> updateCartQuantity(
            @PathVariable Long itemId,
            @RequestParam Long memberId,
            @RequestBody QuantityRequest quantityRequest
    ) {
        cartService.updateQuantity(memberId, itemId, quantityRequest.getQuantity());
        return ResponseEntity.ok("상품 수량이 변경되었습니다.");
    }
}