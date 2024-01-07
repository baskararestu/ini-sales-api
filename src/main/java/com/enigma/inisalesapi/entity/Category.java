package com.enigma.inisalesapi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "m_category")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "name", unique = true, nullable = false, length = 100)
    private String name;
}
