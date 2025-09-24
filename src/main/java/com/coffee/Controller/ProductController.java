package com.coffee.Controller;

import com.coffee.DTO.InsertRequest;
import com.coffee.Entity.Product;
import com.coffee.Service.ProductService;
import com.coffee.constant.Category;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/list")
    public ResponseEntity<?> getProductList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(required = false) Category category) {
        try {
            Page<Product> productPage = productService.findAll(PageRequest.of(page - 1, size), category); // page: 0-based
            Map<String, Object> res = new HashMap<>();
            res.put("products", productPage.getContent());
            res.put("totalPages", productPage.getTotalPages());
            res.put("currentPage", page);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("상품 목록을 불러오는 중 오류가 발생했습니다.");
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody @Valid InsertRequest insertRequest, BindingResult bindingResult) {
        // validation 실패 → 400 Bad Request
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
        }
        // DTO에서 엔티티로 수동 매핑하여 insert
        Product product = new Product();
        product.setName(insertRequest.getName());
        product.setPrice(insertRequest.getPrice());
        product.setCategory(Category.valueOf(insertRequest.getCategory()));
        product.setStock(insertRequest.getStock());
        product.setImage(insertRequest.getImage());
        product.setDescription(insertRequest.getDescription());
        productService.insert(product);
        // 상품 등록 성공 → 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body("상품 등록에 성공했습니다.");
    }









    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        return productService.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("해당 상품이 존재하지 않습니다. id=" + id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.update(id, product)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("해당 상품이 존재하지 않습니다. id=" + id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        if (productService.delete(id)) {
            return ResponseEntity.ok("상품이 삭제되었습니다. id=" + id);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("해당 상품이 존재하지 않습니다. id=" + id);
        }
    }
}
