package com.enigma.inisalesapi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "m_customer_addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CustomerAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String address;
    @Column(length = 50)
    private String city;
    @Column(length = 50)
    private String province;
    @Column(name = "postal_code",length = 20)
    private String postalCode;
    @Column(name = "is_primary")
    private Boolean isPrimary;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
