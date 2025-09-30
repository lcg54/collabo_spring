package com.coffee.Service;

import com.coffee.DTO.CartAddRequest;
import com.coffee.DTO.CartResponse;
import com.coffee.Entity.Cart;
import com.coffee.Entity.CartProduct;
import com.coffee.Entity.Member;
import com.coffee.Entity.Product;
import com.coffee.Repository.CartProductRepository;
import com.coffee.Repository.CartRepository;
import com.coffee.Repository.MemberRepository;
import com.coffee.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public CartResponse getCart(Long memberId) {
        // 회원 찾기
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        // 카트 찾기 (없으면 생성)
        Cart cart = cartRepository.findByMember(member)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setMember(member);
                    return cartRepository.save(newCart);
                });
        // 카트 저장
        return CartResponse.fromEntity(cart);
    }

    public void addItem(Long memberId, CartAddRequest dto) {
        // 회원 찾기
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        // 카트 찾기 (없으면 생성)
        Cart cart = cartRepository.findByMember(member)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setMember(member);
                    return cartRepository.save(newCart);
                });
        // 상품 찾기
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 상품입니다."));
        // 카트상품 찾기 (없으면 생성)
        CartProduct item = cartProductRepository.findByCartAndProduct(cart, product)
                .orElseGet(() -> {
                    CartProduct newItem = new CartProduct();
                    newItem.setCart(cart);
                    newItem.setProduct(product);
                    newItem.setQuantity(0);
                    return newItem;
                });
        // 수량 = 기존 수량 + 추가요청 수량
        item.setQuantity(item.getQuantity() + dto.getQuantity());
        // 아이템 저장
        cartProductRepository.save(item);
    }

    public void removeItem(Long memberId, Long itemId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        Cart cart = cartRepository.findByMember(member)
                .orElseThrow(() -> new RuntimeException("장바구니가 존재하지 않습니다."));
        CartProduct item = cartProductRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 장바구니 상품입니다."));
        if (!item.getCart().equals(cart)) {
            throw new RuntimeException("해당 회원의 장바구니 상품이 아닙니다.");
        }
        cartProductRepository.delete(item);
    }
}
