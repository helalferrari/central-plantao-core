package br.com.centralplantao.api.mapper;

import br.com.centralplantao.api.dto.ContractRequest;
import br.com.centralplantao.api.dto.ContractResponse;
import br.com.centralplantao.api.dto.ContractedShiftRequest;
import br.com.centralplantao.api.dto.ContractedShiftResponse;
import br.com.centralplantao.domain.model.Contract;
import br.com.centralplantao.domain.model.ContractedShift;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface ContractMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "contractedShifts", source = "contractedShifts")
    Contract toEntity(ContractRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contract", ignore = true)
    ContractedShift toEntity(ContractedShiftRequest request);

    ContractResponse toResponse(Contract entity);

    ContractedShiftResponse toResponse(ContractedShift entity);

    List<ContractResponse> toResponseList(List<Contract> entities);
}
