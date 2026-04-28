package br.com.centralplantao.domain.service.strategy;

import br.com.centralplantao.domain.enums.ScheduleType;
import br.com.centralplantao.domain.enums.Workload;
import br.com.centralplantao.domain.model.Contract;
import br.com.centralplantao.domain.model.ContractedShift;
import br.com.centralplantao.domain.model.DutySlot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SlotGenerationTest {

    @Test
    @DisplayName("Should generate exact number of slots per day based on slotQuantity for 12x36 schedule")
    void shouldGenerateCorrectNumberOfSlotsFor12x36() {
        // Arrange
        int slotQuantity = 3;
        YearMonth targetMonth = YearMonth.of(2026, 4); // April 2026

        Contract contract = Contract.builder()
                .startDate(LocalDate.of(2026, 4, 1))
                .endDate(LocalDate.of(2026, 4, 30))
                .build();

        ContractedShift shift = ContractedShift.builder()
                .contract(contract)
                .scheduleType(ScheduleType.SHIFT_12X36)
                .workload(Workload.W12)
                .slotQuantity(slotQuantity)
                .build();

        Shift12x36Generator generator = new Shift12x36Generator();

        // Act
        List<DutySlot> generatedSlots = generator.generate(shift, targetMonth);

        // Assert
        assertThat(generatedSlots).isNotEmpty();
        
        // A 12x36 starting on day 1 means it works on day 1, 3, 5, etc.
        // Let's filter slots that start on April 1st, 2026.
        List<DutySlot> slotsOnDayOne = generatedSlots.stream()
                .filter(slot -> slot.getStartTime().toLocalDate().equals(LocalDate.of(2026, 4, 1)))
                .toList();

        // The multiplicity rule requires 'N' instances for both day and night shifts
        assertThat(slotsOnDayOne)
                .hasSize(slotQuantity * 2)
                .allSatisfy(slot -> {
                    assertThat(slot.getContractedShift()).isEqualTo(shift);
                    assertThat(slot.getStatus().name()).isEqualTo("OPEN");
                });
    }
}
