package com.coffee.Service;

import com.coffee.DTO.OrderListResponse;
import com.coffee.DTO.OrderProductListResponse;
import com.coffee.DTO.OrderProductRequest;
import com.coffee.DTO.OrderRequest;
import com.coffee.Entity.*;
import com.coffee.Repository.CartProductRepository;
import com.coffee.Repository.MemberRepository;
import com.coffee.Repository.OrderRepository;
import com.coffee.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final CartProductRepository cartProductRepository;

    @Transactional
    public void createOrder(OrderRequest dto) {
        // 회원 찾기
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));
        // 주문 생성
        Order order = new Order();
        order.setMember(member);
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus(dto.getOrderStatus());

        // 반복문으로 각 주문상품 처리
        for (OrderProductRequest orderItem : dto.getOrderItems()) {
            // 상품 찾기
            Product product = productRepository.findById(orderItem.getProductId())
                    .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
            // 상품 재고 확인
            if (product.getStock() < orderItem.getQuantity()) {
                throw new RuntimeException("상품 재고가 부족합니다: " + product.getName());
            }
            // 주문상품 생성
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(product);
            orderProduct.setQuantity(orderItem.getQuantity());
            order.getOrderItems().add(orderProduct);
            // 상품 재고 갱신 (기존 재고 - 주문상품 수량)
            product.setStock(product.getStock() - orderItem.getQuantity());

            // 카트 갱신
            CartProduct item = cartProductRepository.findById(orderItem.getCartProductId())
                    .orElseThrow(() -> new RuntimeException("카트상품을 찾을 수 없습니다."));
            int remainingQty = item.getQuantity() - orderItem.getQuantity();
            if (remainingQty <= 0) { // 카트상품 수량 <= 0 이면
                cartProductRepository.delete(item); // 카트에서 상품 제거
            } else { // 카트상품 수량이 남아있으면
                item.setQuantity(remainingQty); // 카트상품 수량 갱신하고
                cartProductRepository.save(item); // 카트상품 저장
            }
        }
        // 주문 저장
        orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<OrderListResponse> getOrderListByMemberId(Long memberId) {
        // 주문 찾기
        List<Order> orders = orderRepository.findByMemberIdOrderByOrderDateDesc(memberId);
        List<OrderListResponse> orderList = new ArrayList<>();
        // 반복문으로 각 주문 처리
        for (Order order : orders) {
            // 내부 반복문으로 각 주문의 주문상품을 리스트화
            List<OrderProductListResponse> orderProductList = new ArrayList<>();
            for (OrderProduct orderProduct : order.getOrderItems()) {
                orderProductList.add(new OrderProductListResponse(
                        orderProduct.getProduct().getId(),
                        orderProduct.getProduct().getName(),
                        orderProduct.getQuantity()
                ));
            }
            // 각 주문을 리스트화
            orderList.add(new OrderListResponse(
                    order.getId(),
                    order.getOrderDate(),
                    order.getOrderStatus(),
                    orderProductList
            ));
        }
        // 주문 리스트 리턴
        return orderList;
    }
}