package com.enigma.inisalesapi.repository;

import com.enigma.inisalesapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {
    @Modifying
    @Query(value = "INSERT INTO m_products (id, product_detail_id, product_price_id) " +
            "VALUES (:productId, :productDetailId, :productPriceId)", nativeQuery = true)
    void insertProductNative(
            @Param("productId") String productId,
            @Param("productDetailId") String productDetailId,
            @Param("productPriceId") String productPriceId
    );

    @Query(value = "SELECT mpd.name as product_name, mc.name as category_name FROM Product mp " +
            "JOIN ProductDetail mpd ON mpd.id = mp.productDetail.id " +
            "JOIN Category mc ON mc.id = mpd.category.id " +
            "WHERE mpd.name = :productName AND mc.name = :categoryName")
    Optional<Product> findByNameAndCategory(
            @Param("productName") String productName,
            @Param("categoryName") String categoryName
    );
    Optional<Product>findProductByProductDetailNameAndProductDetailCategoryName(String productName,String categoryName);
}
