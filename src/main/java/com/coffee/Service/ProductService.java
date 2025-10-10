package com.coffee.Service;

import com.coffee.DTO.ProductInsertRequest;
import com.coffee.Entity.Product;
import com.coffee.Repository.ProductRepository;
import com.coffee.constant.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Page<Product> getFilteredProducts(Pageable pageable, Category category, String period, String searchType, String keyword) {
        try {
            LocalDate startDate = null;
            if (period != null) {
                switch (period) {
                    case "1M" -> startDate = LocalDate.now().minusMonths(1);
                    case "6M" -> startDate = LocalDate.now().minusMonths(6);
                    case "1Y" -> startDate = LocalDate.now().minusYears(1);
                }
            }
            return productRepository.findAllByFilters(category, startDate, searchType, keyword, pageable);

        } catch (Exception e) {
            throw new RuntimeException("상품 목록을 불러오는 중 오류가 발생했습니다.", e);
        }
    }

    public Product detail(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 상품입니다."));
    }

    public void insert(ProductInsertRequest dto) {
        Product product = new Product();
        dto.applyToEntity(product);
        productRepository.save(product);
    }

    public void update(Long id, ProductInsertRequest dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 상품입니다."));
        dto.applyToEntity(product);
        productRepository.save(product);
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("존재하지 않는 상품입니다.");
        }
        productRepository.deleteById(id);
    }

    public List<Product> findByImageKeyword(String keyword) {
        return productRepository.findByImageContaining(keyword);
    }
}