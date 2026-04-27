package br.com.centralplantao.api.dto;

import java.time.LocalDate;
import java.util.List;

public record ContractResponse(
    Long id,
    String name,
    LocalDate startDate,
    LocalDate endDate,
    boolean active,
    List<ContractedShiftResponse> contractedShifts
) {
}
