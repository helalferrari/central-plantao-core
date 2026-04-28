package br.com.centralplantao.domain.model;

import br.com.centralplantao.domain.enums.SlotStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Entity
@Table(name = "duty_slots")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DutySlot {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contracted_shift_id", nullable = false)
    private ContractedShift contractedShift;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professional_id")
    private Professional professional;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SlotStatus status;

    public static DutySlot create(ContractedShift contractedShift, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("[SLOT-DOMAIN] - Initializing slot: {}", startTime);
        return DutySlot.builder()
                .contractedShift(contractedShift)
                .startTime(startTime)
                .endTime(endTime)
                .status(SlotStatus.OPEN)
                .build();
    }
}
