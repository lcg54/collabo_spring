package com.coffee.DTO;

import com.coffee.Entity.Product;
import com.coffee.constant.Category;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductInsertRequest {
    @NotBlank(message = "상품명은 필수 입력 사항입니다.")
    private String name;

    @NotNull(message = "가격은 필수 입력 사항입니다.")
    @Min(value = 100, message = "가격은 100원 이상이어야 합니다.")
    private Integer price;

    @NotBlank(message = "카테고리를 선택해주세요.")
    private String category;

    @NotNull(message = "재고 수량은 필수 입력 사항입니다.")
    @Min(value = 10, message = "재고 수량은 10개 이상이어야 합니다.")
    @Max(value = 1000, message = "재고 수량은 1000개 이하이어야 합니다.")
    private Integer stock;

    @NotBlank(message = "이미지는 필수 입력 사항입니다.")
    private String image;

    @NotBlank(message = "상품 설명은 필수 입력 사항입니다.")
    private String description;

    public void applyToEntity(Product product) {
        product.setName(this.name);
        product.setPrice(this.price);
        product.setCategory(Category.valueOf(this.category));
        product.setStock(this.stock);
        product.setImage(this.image);
        product.setDescription(this.description);
    }
}