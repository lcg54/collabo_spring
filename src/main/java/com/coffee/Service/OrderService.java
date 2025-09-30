package com.coffee.Service;

import com.coffee.DTO.OrderProductRequest;
import com.coffee.DTO.OrderRequest;
import com.coffee.Entity.Member;
import com.coffee.Entity.Order;
import com.coffee.Entity.OrderProduct;
import com.coffee.Entity.Product;
import com.coffee.Repository.MemberRepository;
import com.coffee.Repository.OrderRepository;
import com.coffee.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public void createOrder(OrderRequest dto) {
        // 회원 찾기
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));
        // 주문 생성
        Order order = new Order();
        order.setMember(member);
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus(dto.getOrderStatus());

        for (OrderProductRequest item : dto.getOrderItems()) {
            // 상품 찾기
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
            // 재고 확인
            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("상품 재고가 부족합니다: " + product.getName());
            }
            // 주문상품 생성
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(product);
            orderProduct.setQuantity(item.getQuantity());
            // 재고 = 기존 재고 - 주문 재고
            product.setStock(product.getStock() - item.getQuantity());
            // 저장할 주문상품 저장
            order.getOrderItems().add(orderProduct);

            // 여기서 주문한 상품 제거까지 구현하면 되지 않을까
            // set카트재고(기존 카트재고 - 주문 재고)
            // 만약 0이면 상품 제거
        }
        // 주문상품 저장
        orderRepository.save(order);
    }
}