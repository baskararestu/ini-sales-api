package com.enigma.inisalesapi.dto.response;

import com.enigma.inisalesapi.entity.ProductPrice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProductResponse {
    private String productId;
    private String productName;
    private String productCategory;
    private Long price;
    private Integer stock;
    private List<ProductPrice>priceList;
}
