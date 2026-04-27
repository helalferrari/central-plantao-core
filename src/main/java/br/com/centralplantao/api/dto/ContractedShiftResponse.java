package br.com.centralplantao.api.dto;

import br.com.centralplantao.domain.enums.ScheduleType;
import br.com.centralplantao.domain.enums.ProfessionalType;

public record ContractedShiftResponse(
    Long id,
    String sectorDescription,
    Integer slotQuantity,
    String workload,
    ScheduleType scheduleType,
    ProfessionalType professionalType
) {
}
