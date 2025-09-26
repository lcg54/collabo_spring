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
            Page<Product> productPage = productService.findAll(PageRequest.of(page - 1, size), category);
            Map<String, Object> resMap = new HashMap<>();
            resMap.put("products", productPage.getContent());
            resMap.put("totalPages", productPage.getTotalPages());
            resMap.put("currentPage", page);
            return ResponseEntity.status(HttpStatus.OK).body(resMap);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("상품 목록을 불러오는 중 오류가 발생했습니다.");
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody @Valid InsertRequest insertRequest, BindingResult bindingResult) {
        // validation 실패 → 400 Bad Request
        if (bindingResult.hasErrors()) {
            Map<String, String> errMap = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errMap.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errMap);
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
        return ResponseEntity.status(HttpStatus.CREATED).body("상품이 성공적으로 등록되었습니다.");
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getProductDetail(@PathVariable Long id) {
        Product product = this.productService.findById(id);
        // 상품 없음 → 404 NOT_FOUND
        if (product == null) {
            Map<String, String> errMap = new HashMap<>();
            errMap.put("id", "해당 상품이 존재하지 않습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errMap);
        }
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        if (!productService.delete(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 상품이 존재하지 않습니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body("상품이 삭제되었습니다.");
    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product product) {
//        return productService.update(id, product)
//                .<ResponseEntity<?>>map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body("해당 상품이 존재하지 않습니다. id=" + id));
//    }


}
