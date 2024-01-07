package com.enigma.inisalesapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "m_product_price")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProductPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "stock", columnDefinition = "int check(stock>0)")
    private Integer stock;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "price", columnDefinition = "bigint check(price>0)")
    private Long price;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;
}
