package com.coffee.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter @Setter @RequiredArgsConstructor @AllArgsConstructor @ToString
@Entity
@Table(name="carts")
public class Cart {
    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY) // 카트 하나에 멤버 하나 대응
    @JoinColumn(name = "member_id")
    private Member member;

    // 카트 하나에 여러 카트상품이 담길 수 있으므로 컬렉션 형태로 작성
    // CascadeType.ALL : 카트 정보에 변경/수정/삭제 등의 변동사항이 발생하면, 카트 상품에 전부 반영
    // CascadeType과 관련하여 db의 on delete set null, on delete cascade 공부
    // 주의) "cart" : 연관 관계의 주인 엔터티에 들어있는 변수명과 반드시 일치
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartProduct> cartProducts;
}
