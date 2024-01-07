package com.enigma.inisalesapi.entity;

import com.enigma.inisalesapi.constant.ERole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "m_role")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Enumerated(EnumType.STRING)
    private ERole roleName;
}
