package com.coffee.Repository;

import com.coffee.Entity.Product;
import com.coffee.constant.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("""
        SELECT p FROM Product p
        WHERE (:category IS NULL OR p.category = :category)
        AND (:startDate IS NULL OR p.regDate >= :startDate)
        AND (:keyword IS NULL
            OR (:searchType = 'TITLE' AND LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')))
            OR (:searchType = 'CONTENT' AND LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')))
            OR (:searchType IS NULL AND (LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))))
        )
        ORDER BY p.id DESC
    """)
    Page<Product> findAllByFilters(
            @Param("category") Category category,
            @Param("startDate") LocalDate startDate,
            @Param("searchType") String searchType,
            @Param("keyword") String keyword,
            Pageable pageable
    );

    List<Product> findByImageContaining(String keyword);
}