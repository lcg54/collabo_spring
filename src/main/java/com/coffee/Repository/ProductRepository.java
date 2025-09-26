package com.coffee.Repository;

import com.coffee.Entity.Product;
import com.coffee.constant.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByCategory(Category category, Pageable pageable);
    // Optional<Product> findById(Long id); 선언 안해도 이미 제공
}