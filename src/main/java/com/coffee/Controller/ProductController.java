package com.coffee.Controller;

import com.coffee.DTO.ProductInsertRequest;
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
import java.util.List;
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
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) String period,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String keyword
    ) {
        Page<Product> productPage = productService.getFilteredProducts(PageRequest.of(page - 1, size), category, period, searchType, keyword);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("products", productPage.getContent());
        resMap.put("totalPages", productPage.getTotalPages());
        resMap.put("currentPage", page);
        return ResponseEntity.ok(resMap);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getProductDetail(@PathVariable Long id) {
        Product product = productService.detail(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PostMapping("/insert")
    public ResponseEntity<?> insertProduct(@RequestBody @Valid ProductInsertRequest dto, BindingResult result) {
        // validation 실패 → 400 Bad Request, 사용자에게 보여줄 에러 메시지
        if (result.hasErrors()) {
            Map<String, String> errMap = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errMap.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errMap);
        }
        productService.insert(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("상품이 성공적으로 등록되었습니다.");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductInsertRequest dto, BindingResult result) {
        // validation 실패 → 400 Bad Request, 사용자에게 보여줄 에러 메시지
        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
        }
        productService.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body("상품이 수정되었습니다.");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("상품이 삭제되었습니다.");
    }

    @GetMapping("/images")
    public ResponseEntity<List<Product>> getBigImages() {
        List<Product> products = productService.findByImageKeyword("bigsize");
        return ResponseEntity.ok(products);
    }
}
