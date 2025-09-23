package com.coffee.test;

import com.coffee.Entity.Product;
import com.coffee.Repository.ProductRepository;
import com.coffee.common.GenerateData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProductTest {
    @Autowired
    private ProductRepository productRepository;

    @Test @DisplayName("이미지를 이용한 데이터 추가")
    public void insertProductList(){
        GenerateData generateData = new GenerateData();

        List<String> imageNameList = generateData.getImageFileNames();
        System.out.println("총 이미지 개수 : " + imageNameList.size());

        for (int i = 0; i < imageNameList.size(); i++) {
            Product bean = generateData.insertProduct(1, imageNameList.get(i));
            this.productRepository.save(bean);
        }
    }
}
