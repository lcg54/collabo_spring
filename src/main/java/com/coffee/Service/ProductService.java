package com.coffee.Service;

import com.coffee.Entity.Product;
import com.coffee.Repository.ProductRepository;
import com.coffee.constant.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Page<Product> findAll(Pageable pageable, Category category) {
        if (category != null) {
            return productRepository.findByCategory(category, pageable);
        } else {
            return productRepository.findAll(pageable);
        }
    }

    public void insert(Product product) {
        productRepository.save(product);
    }









    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Optional<Product> update(Long id, Product product) {
        return productRepository.findById(id)
                .map(p -> {
                    p.setName(product.getName());
                    p.setPrice(product.getPrice());
                    p.setCategory(product.getCategory());
                    p.setStock(product.getStock());
                    p.setImage(product.getImage());
                    p.setDescription(product.getDescription());
                    return productRepository.save(p);
                });
    }

    public boolean delete(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}