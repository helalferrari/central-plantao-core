package br.com.centralplantao.domain.model;

import br.com.centralplantao.domain.enums.ScheduleType;
import br.com.centralplantao.domain.enums.ProfessionalType;
import br.com.centralplantao.domain.enums.Workload;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contracted_shifts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractedShift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sector_description", nullable = false)
    private String sectorDescription;

    @Column(name = "slot_quantity", nullable = false)
    private Integer slotQuantity;

    @Column(name = "start_hour", nullable = false)
    private java.time.LocalTime startHour = java.time.LocalTime.of(7, 0);

    @Enumerated(EnumType.STRING)
    @Column(name = "workload", nullable = false)
    private Workload workload;

    @Enumerated(EnumType.STRING)
    @Column(name = "schedule_type", nullable = false)
    private ScheduleType scheduleType;

    @Enumerated(EnumType.STRING)
    @Column(name = "professional_type", nullable = false)
    private ProfessionalType professionalType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;
}
