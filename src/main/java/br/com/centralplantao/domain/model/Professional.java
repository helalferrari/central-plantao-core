package br.com.centralplantao.domain.model;

import br.com.centralplantao.domain.enums.ProfessionalType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "professionals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Professional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String document;

    @Column(name = "registration_number", nullable = false)
    private String registrationNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "professional_type", nullable = false)
    private ProfessionalType professionalType;

    @Column(nullable = false)
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}
