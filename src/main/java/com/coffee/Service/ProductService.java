package com.coffee.Service;

import com.coffee.Entity.Product;
import com.coffee.Repository.ProductRepository;
import com.coffee.constant.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 상품입니다."));
        // .get : 무조건 있다고 확신하면 사용 가능. 근데 url로 들어갈 수 있으니까 안쓰는걸로
    }

    public boolean delete(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

//    public Optional<Product> update(Long id, Product product) {
//        return productRepository.findById(id)
//                .map(p -> {
//                    p.setName(product.getName());
//                    p.setPrice(product.getPrice());
//                    p.setCategory(product.getCategory());
//                    p.setStock(product.getStock());
//                    p.setImage(product.getImage());
//                    p.setDescription(product.getDescription());
//                    return productRepository.save(p);
//                });
//    }
}