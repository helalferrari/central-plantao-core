package br.com.centralplantao.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "corporate_name", nullable = false)
    private String corporateName;

    @Column(name = "trade_name", nullable = false)
    private String tradeName;

    @Column(nullable = false, unique = true)
    private String document;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private boolean active;
}
