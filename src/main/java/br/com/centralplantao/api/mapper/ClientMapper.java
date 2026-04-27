package br.com.centralplantao.api.mapper;

import br.com.centralplantao.api.dto.ClientRequest;
import br.com.centralplantao.api.dto.ClientResponse;
import br.com.centralplantao.domain.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mapping(target = "id", ignore = true)
    Client toEntity(ClientRequest request);

    ClientResponse toResponse(Client client);

    List<ClientResponse> toResponseList(List<Client> clients);
}
