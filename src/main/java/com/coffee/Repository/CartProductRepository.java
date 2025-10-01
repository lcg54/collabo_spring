package com.coffee.Repository;

import com.coffee.Entity.Cart;
import com.coffee.Entity.CartProduct;
import com.coffee.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    Optional<CartProduct> findByCartAndProduct(Cart cart, Product product);
}
