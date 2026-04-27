package br.com.centralplantao.api.dto;

import br.com.centralplantao.domain.enums.ProfessionalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProfessionalRequest(
    @NotBlank @Size(max = 255) String fullName,
    @NotBlank @Size(max = 20) String document,
    @NotBlank @Size(max = 50) String registrationNumber,
    @NotNull ProfessionalType professionalType,
    @NotNull Long clientId,
    boolean active
) {}
