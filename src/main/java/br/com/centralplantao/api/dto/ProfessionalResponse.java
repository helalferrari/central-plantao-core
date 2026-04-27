package br.com.centralplantao.api.dto;

import br.com.centralplantao.domain.enums.ProfessionalType;

public record ProfessionalResponse(
    Long id,
    String fullName,
    String document,
    String registrationNumber,
    ProfessionalType professionalType,
    Long clientId,
    String clientName,
    boolean active
) {}
