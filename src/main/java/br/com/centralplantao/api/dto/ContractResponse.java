package br.com.centralplantao.api.dto;

import java.time.LocalDate;
import java.util.List;

public record ContractResponse(
    Long id,
    String description,
    LocalDate startDate,
    LocalDate endDate,
    boolean active,
    ClientResponse client,
    List<ContractedShiftResponse> contractedShifts
) {
}
