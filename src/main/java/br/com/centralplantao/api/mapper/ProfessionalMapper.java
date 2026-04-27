package br.com.centralplantao.api.mapper;

import br.com.centralplantao.api.dto.ProfessionalRequest;
import br.com.centralplantao.api.dto.ProfessionalResponse;
import br.com.centralplantao.domain.model.Professional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProfessionalMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    Professional toEntity(ProfessionalRequest request);

    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "clientName", source = "client.tradeName")
    ProfessionalResponse toResponse(Professional professional);

    List<ProfessionalResponse> toResponseList(List<Professional> professionals);
}
