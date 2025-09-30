package com.coffee.Repository;

import com.coffee.Entity.Cart;
import com.coffee.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    // Optional : null 처리를 명시적으로 강제하여 NPE 발생을 막음 (.map(), .filter(), .orElse() 등 체이닝)
    // 찾은 경우 → Optional.of(member), 없는 경우 → Optional.empty()
    Optional<Cart> findByMember(Member member) ;
}
