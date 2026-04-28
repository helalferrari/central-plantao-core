package br.com.centralplantao.api.mapper;

import br.com.centralplantao.api.dto.SlotResponse;
import br.com.centralplantao.domain.model.DutySlot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SlotMapper {
    @Mapping(target = "professionalName", source = "professional.fullName")
    @Mapping(target = "professionalType", source = "contractedShift.professionalType")
    @Mapping(target = "sectorDescription", source = "contractedShift.sectorDescription")
    SlotResponse toResponse(DutySlot slot);

    List<SlotResponse> toResponseList(List<DutySlot> slots);
}
