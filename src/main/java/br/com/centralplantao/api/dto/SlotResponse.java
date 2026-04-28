package br.com.centralplantao.api.dto;

import br.com.centralplantao.domain.enums.ProfessionalType;
import br.com.centralplantao.domain.enums.SlotStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record SlotResponse(
    UUID id,
    LocalDateTime startTime,
    LocalDateTime endTime,
    SlotStatus status,
    String professionalName,
    ProfessionalType professionalType,
    String sectorDescription
) {}
