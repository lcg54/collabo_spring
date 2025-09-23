package com.coffee.Controller;

import com.coffee.Entity.Product;
import com.coffee.Service.ProductService;
import com.coffee.constant.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController @RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/list")
    public Map<String, Object> getProductPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(required = false) Category category) {
        Page<Product> productPage = productService.findAll(PageRequest.of(page - 1, size), category); // Page : 0-based

        Map<String, Object> res = new HashMap<>();
        res.put("products", productPage.getContent());
        res.put("totalPages", productPage.getTotalPages());
        res.put("currentPage", page);
        return res;
    }
}
