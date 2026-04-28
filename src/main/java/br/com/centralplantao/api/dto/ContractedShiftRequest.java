package br.com.centralplantao.api.dto;

import br.com.centralplantao.domain.enums.ScheduleType;
import br.com.centralplantao.domain.enums.ProfessionalType;
import br.com.centralplantao.domain.enums.Workload;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ContractedShiftRequest(
    @NotBlank @Size(max = 255) String sectorDescription,
    @NotNull @Min(1) Integer slotQuantity,
    @NotNull Workload workload,
    @NotNull java.time.LocalTime startHour,
    @NotNull ScheduleType scheduleType,
    @NotNull ProfessionalType professionalType
) {
}
