package br.com.centralplantao.domain.service.strategy;

import br.com.centralplantao.domain.enums.ScheduleType;
import br.com.centralplantao.domain.model.ContractedShift;
import br.com.centralplantao.domain.model.DutySlot;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Shift12x36Generator implements SlotGenerationStrategy {

    @Override
    public List<DutySlot> generate(ContractedShift shift, YearMonth targetMonth) {
        log.info("[REFACTOR] - Generating 24h coverage for 12x36 scale. 2 shifts/day created.");
        
        List<DutySlot> slots = new ArrayList<>();
        LocalDate contractStart = shift.getContract().getStartDate();
        LocalDate contractEnd = shift.getContract().getEndDate();
        LocalDate targetStart = targetMonth.atDay(1);
        LocalDate targetEnd = targetMonth.atEndOfMonth();

        // Se o contrato começa depois do fim do mês alvo, não há nada a gerar
        if (contractStart.isAfter(targetEnd)) return slots;
        
        // Se o contrato terminou antes do início do mês alvo, não há nada a gerar
        if (contractEnd != null && contractEnd.isBefore(targetStart)) return slots;

        LocalDate currentDay = contractStart;
        int stepDays = (shift.getScheduleType() == ScheduleType.SHIFT_24X48) ? 3 : 2;

        // Alinha o primeiro dia de trabalho com o início do mês solicitado
        while (currentDay.isBefore(targetStart)) {
            currentDay = currentDay.plusDays(stepDays);
        }

        int positions = shift.getSlotQuantity();
        LocalTime startHour = shift.getStartHour() != null ? shift.getStartHour() : LocalTime.of(7, 0);
        
        while (!currentDay.isAfter(targetEnd) && (contractEnd == null || !currentDay.isAfter(contractEnd))) {
            // Slot A (Day Shift)
            LocalDateTime startDay = currentDay.atTime(startHour);
            LocalDateTime endDay = startDay.plusHours(12);

            // Slot B (Night Shift)
            LocalDateTime startNight = endDay;
            LocalDateTime endNight = startNight.plusHours(12);

            for (int i = 0; i < positions; i++) {
                slots.add(DutySlot.create(shift, startDay, endDay));
                slots.add(DutySlot.create(shift, startNight, endNight));
            }
            currentDay = currentDay.plusDays(stepDays);
        }

        return slots;
    }
}
