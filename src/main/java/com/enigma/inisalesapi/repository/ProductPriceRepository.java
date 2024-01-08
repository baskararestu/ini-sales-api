package com.enigma.inisalesapi.repository;

import com.enigma.inisalesapi.entity.Admin;
import com.enigma.inisalesapi.entity.ProductPrice;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductPriceRepository extends JpaRepository<ProductPrice, String> {
    @Modifying
    @Query(value = "INSERT INTO m_product_price (id, is_active, price, stock, created_at) " +
            "VALUES (?, ?, ?, ?, ?)", nativeQuery = true)
    void insertProductPriceNative(
            @Param("id") String id,
            @Param("is_active") Boolean isActive,
            @Param("price") Long price,
            @Param("stock") Integer stock,
            @Param("created_at") LocalDateTime createdAt
    );
    @Query(value = "SELECT * FROM m_product_price WHERE id = :id", nativeQuery = true)
    ProductPrice findProductPricesByIdNative(@Param("id") String id);
    @Query(value = "SELECT id FROM m_product_price ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    String findLastInsertedId();
}

