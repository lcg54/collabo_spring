package com.coffee.Service;

import com.coffee.Entity.CartProduct;
import com.coffee.Repository.CartProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartProductService {
    private final CartProductRepository cartProductRepository ;

    public void saveCartProduct(CartProduct cp) {
        this.cartProductRepository.save(cp);
    }
}
