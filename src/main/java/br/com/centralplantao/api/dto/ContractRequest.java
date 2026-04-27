package br.com.centralplantao.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record ContractRequest(
    @NotBlank @Size(max = 255) String description,
    @NotNull LocalDate startDate,
    @NotNull LocalDate endDate,
    boolean active,
    @NotNull Long clientId,
    @NotEmpty @Valid List<ContractedShiftRequest> contractedShifts
) {
}
