package com.coffee.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @RequiredArgsConstructor @AllArgsConstructor @ToString
@Entity
@Table(name="carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY) // 카트 하나에 멤버 하나 대응
    @JoinColumn(name = "member_id")
    private Member member;

    // mappedBy = "cart" : 자식(CartProduct)에서 호출한 부모(Cart)의 변수명과 반드시 일치해야 함
    // cascade = CascadeType.ALL : 부모를 저장/삭제할 때, 자식도 함께 반영 (DB : on delete set null, on delete cascade)
    // orphanRemoval = true : 부모 리스트에서 빠진 자식은 DB에서도 제거 (=고아 제거)
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true) // 카트 하나에 카트상품 다수 대응
    private List<CartProduct> items = new ArrayList<>();

    public void addItem(CartProduct item) {
        items.add(item);
        item.setCart(this);
    }
}
