package com.coffee.Controller;

import com.coffee.DTO.ProductInsertRequest;
import com.coffee.Entity.Product;
import com.coffee.Service.ProductService;
import com.coffee.constant.Category;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @Value("${productImageLocation}")
    private String productImageLocation ; // 기본 값 : null

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
        // 이미지 저장
        if(dto.getImage() != null && dto.getImage().startsWith("data:image")) {
            String imageFileName = saveProductImage(dto.getImage());
            dto.setImage(imageFileName);
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
        // 이미지 저장
        if(dto.getImage() != null && dto.getImage().startsWith("data:image")) {
            Product existingProduct = productService.detail(id);
            String oldImage = existingProduct.getImage();
            if (oldImage != null && !oldImage.isBlank()) {
                File oldFile = new File(getProductImagePath() + oldImage);
                oldFile.delete();
            }
            String imageFileName = saveProductImage(dto.getImage());
            dto.setImage(imageFileName);
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


    // Base64 인코딩 문자열을 변환하여 이미지로 만들고, 저장해주는 메소드
    private String saveProductImage(String base64Image) {
        // 데이터 베이스와 이미지 경로에 저장될 이미지의 이름
        String imageFileName = "product_" + System.currentTimeMillis() + ".jpg" ;
        String pathName = getProductImagePath();
        File imageFile = new File(pathName + imageFileName) ;
        // base64Image : JavaScript FileReader API에 만들어진 이미지
        // 메소드 체이닝 : 점을 연속적으로 찍어서 메소드를 계속 호출하는 것
        byte[] decodedImage = Base64.getDecoder().decode(base64Image.split(",")[1]);

        try{ // FileOutputStream : 바이트 파일을 처리해주는 자바의 Stream 클래스
            // 파일 정보를 byte 단위로 변환하여 이미지를 복사
            FileOutputStream fos = new FileOutputStream(imageFile) ;
            fos.write(decodedImage);
            return imageFileName ;

        }catch (Exception err){
            err.printStackTrace();
            return base64Image ;
        }
    }

    // 이미지 경로를 반환하는 메서드
    private String getProductImagePath() {
        // 폴더 구분자가 제대로 설정되어 있지 않으면 추가
        // File.separator : 폴더 구분자. 리눅스는 /, 윈도우는 \
        return productImageLocation.endsWith("\\") || productImageLocation.endsWith("/")
                ? productImageLocation
                : productImageLocation + File.separator;
    }
}
