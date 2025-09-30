package com.coffee.Repository;

import com.coffee.Entity.Cart;
import com.coffee.Entity.CartProduct;
import com.coffee.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    Optional<CartProduct> findByCartAndProduct(Cart cart, Product product);
}
