package com.coffee.common;

import com.coffee.Entity.Product;
import com.coffee.constant.Category;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

// 테스트용 임시 데이터 생성
public class GenerateData {
    // 테스트에선 @Value 사용 불가라 직접 경로 지정
    private final String imageForder = "c:\\shop\\images";

    public List<String> getImageFileNames() {
        File folder = new File(imageForder);
        List<String> imageFileNames = new ArrayList<>();
        if(!folder.exists() || !folder.isDirectory()){
            System.out.println(imageForder + "폴더가 존재하지 않습니다.");
            return imageFileNames;
        }
        String[] imageExtensions = {".jpg", ".jpeg", ".png"};
        File[] fileList = folder.listFiles();
        if (fileList != null) {
            for (File file : fileList) {
                // 소문자로 변경 후 확장자 비교
                if (file.isFile()
                        && Arrays.stream(imageExtensions)
                        .anyMatch(ext -> file.getName().toLowerCase().endsWith(ext))) {
                    imageFileNames.add(file.getName());
                }
            }
        }
        return imageFileNames;
    }

    public Product insertProduct(int index, String imageName) {
        Product product = new Product();

        switch (index % 3) {
            case 0: product.setCategory(Category.BREAD);break;
            case 1: product.setCategory(Category.BEVERAGE);break;
            case 2: product.setCategory(Category.CAKE);break;
        }
        String productName = getProductName();
        product.setName(productName);
        String description = getDescriptionData(productName);
        product.setDescription(description);
        product.setImage(imageName);
        product.setPrice(1000 * getRandomDataRange(1, 10));
        product.setStock(111 * getRandomDataRange(1, 9));
        LocalDate sysdate = LocalDate.now();
        product.setRegDate(sysdate.minusDays(index));
        return product;
    }

    private int getRandomDataRange(int start, int end) {
        // start <= somedata <= end
        return new Random().nextInt(end) + start;
    }

    private String getDescriptionData(String name) {
        String[] description = {"엄청 달아요.", "맛있어요.", "맛없어요.", "떫어요.", "엄청 떫어요.", "아주 떫어요.", "새콤해요.", "아주 상큼해요.", "아주 달아요."};
        return name + "는(은) " + description[new Random().nextInt(description.length)];
    }

    private String getProductName() {
        String[] fruits = {"아메리카노", "바닐라라떼", "우유", "에스프레소", "크로아상", "치아바타", "당근 케이크"};
        return fruits[new Random().nextInt(fruits.length)];
    }
}