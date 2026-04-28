package br.com.centralplantao.domain.service.strategy;

import br.com.centralplantao.domain.model.ContractedShift;
import br.com.centralplantao.domain.model.DutySlot;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Component
public class FixedDayGenerator implements SlotGenerationStrategy {

    @Override
    public List<DutySlot> generate(ContractedShift shift, YearMonth targetMonth) {
        List<DutySlot> slots = new ArrayList<>();
        LocalDate contractStart = shift.getContract().getStartDate();
        LocalDate contractEnd = shift.getContract().getEndDate();
        LocalDate targetStart = targetMonth.atDay(1);
        LocalDate targetEnd = targetMonth.atEndOfMonth();
        
        // Se o contrato começa depois do fim do mês alvo, não há nada a gerar
        if (contractStart.isAfter(targetEnd)) return slots;
        
        // Se o contrato terminou antes do início do mês alvo, não há nada a gerar
        if (contractEnd != null && contractEnd.isBefore(targetStart)) return slots;

        // O dia inicial é o que for maior: o dia 1 do mês ou a data de início do contrato
        LocalDate currentDay = targetStart.isAfter(contractStart) ? targetStart : contractStart;

        int positions = shift.getSlotQuantity();
        
        while (!currentDay.isAfter(targetEnd) && (contractEnd == null || !currentDay.isAfter(contractEnd))) {
            // Regra base: pular domingos para escalas fixas (6x1, 5x2)
            if (currentDay.getDayOfWeek() != DayOfWeek.SUNDAY) {
                LocalDateTime start = currentDay.atTime(LocalTime.of(8, 0));
                LocalDateTime end = start.plusHours(shift.getWorkload().getHours());

                for (int i = 0; i < positions; i++) {
                    slots.add(DutySlot.create(shift, start, end));
                }
            }
            currentDay = currentDay.plusDays(1);
        }

        return slots;
    }
}
