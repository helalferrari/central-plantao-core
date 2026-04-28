package br.com.centralplantao.domain.repository;

import br.com.centralplantao.domain.model.DutySlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface DutySlotRepository extends JpaRepository<DutySlot, UUID> {
    
    @Query("SELECT COUNT(d) FROM DutySlot d WHERE d.contractedShift.id = :shiftId AND d.startTime >= :start AND d.startTime < :end")
    long countByContractedShiftIdAndMonth(Long shiftId, LocalDateTime start, LocalDateTime end);

    @Query("SELECT d FROM DutySlot d WHERE d.contractedShift.contract.id = :contractId AND d.startTime >= :start AND d.startTime < :end ORDER BY d.startTime ASC")
    List<DutySlot> findByContractIdAndPeriod(Long contractId, LocalDateTime start, LocalDateTime end);
}
