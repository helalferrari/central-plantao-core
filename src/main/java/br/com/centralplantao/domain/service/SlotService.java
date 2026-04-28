package br.com.centralplantao.domain.service;

import br.com.centralplantao.domain.enums.ScheduleType;
import br.com.centralplantao.domain.model.Contract;
import br.com.centralplantao.domain.model.ContractedShift;
import br.com.centralplantao.domain.model.DutySlot;
import br.com.centralplantao.domain.repository.DutySlotRepository;
import br.com.centralplantao.domain.service.strategy.SlotGenerationFactory;
import br.com.centralplantao.domain.service.strategy.SlotGenerationStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SlotService {

    private final DutySlotRepository dutySlotRepository;
    private final ContractService contractService;
    private final SlotGenerationFactory slotGenerationFactory;

    @Transactional
    public void generateMonthlySlots(Long contractId, YearMonth month) {
        log.info("[SLOT-SERVICE] - Starting monthly generation for Contract ID: {} and Month: {}", contractId, month);

        Contract contract = contractService.findById(contractId);
        List<DutySlot> generatedSlots = new ArrayList<>();
        
        LocalDateTime startOfMonth = month.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = month.atEndOfMonth().atTime(23, 59, 59, 999999999);

        for (ContractedShift shift : contract.getContractedShifts()) {
            long existingCount = dutySlotRepository.countByContractedShiftIdAndMonth(shift.getId(), startOfMonth, endOfMonth);
            
            if (existingCount > 0) {
                log.info("[SLOT-SERVICE] - Skipping shift ID: {}. Slots already exist for the requested month.", shift.getId());
                continue;
            }

            if (shift.getScheduleType() == ScheduleType.SHIFT_12X36) {
                log.info("[SLOT-SERVICE] - Validation: Shift ID: {} is 12x36. Enforcing 24h coverage (2 slots/day).", shift.getId());
            }

            SlotGenerationStrategy strategy = slotGenerationFactory.getStrategy(shift.getScheduleType());
            List<DutySlot> slots = strategy.generate(shift, month);
            generatedSlots.addAll(slots);
        }

        if (!generatedSlots.isEmpty()) {
            dutySlotRepository.saveAll(generatedSlots);
            log.info("[SLOT-SERVICE] - Task completed: {} slots created for Contract {}.", generatedSlots.size(), contractId);
        } else {
            log.info("[SLOT-SERVICE] - Task completed: 0 slots created for Contract {}. (Already generated or out of bounds).", contractId);
        }
    }

    public List<DutySlot> findByContractAndMonth(Long contractId, YearMonth month) {
        log.info("[SLOT-SERVICE] - Fetching slots for Contract ID: {} and Month: {}", contractId, month);
        LocalDateTime startOfMonth = month.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = month.atEndOfMonth().atTime(23, 59, 59, 999999999);
        return dutySlotRepository.findByContractIdAndPeriod(contractId, startOfMonth, endOfMonth);
    }
}
