package br.com.centralplantao.api.dto;

import br.com.centralplantao.domain.enums.ScheduleType;
import br.com.centralplantao.domain.enums.ProfessionalType;
import br.com.centralplantao.domain.enums.Workload;

public record ContractedShiftResponse(
    Long id,
    String sectorDescription,
    Integer slotQuantity,
    Workload workload,
    ScheduleType scheduleType,
    ProfessionalType professionalType
) {
}
