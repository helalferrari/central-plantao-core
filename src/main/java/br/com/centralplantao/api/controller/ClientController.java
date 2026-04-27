package br.com.centralplantao.api.controller;

import br.com.centralplantao.api.dto.ClientRequest;
import br.com.centralplantao.api.dto.ClientResponse;
import br.com.centralplantao.api.mapper.ClientMapper;
import br.com.centralplantao.domain.model.Client;
import br.com.centralplantao.domain.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponse create(@RequestBody @Valid ClientRequest request) {
        log.info("[CLIENT-CONTROLLER] - Received request to create client: {}", request.tradeName());
        Client client = clientMapper.toEntity(request);
        Client saved = clientService.saveClient(client);
        return clientMapper.toResponse(saved);
    }

    @GetMapping
    public List<ClientResponse> list() {
        log.info("[CLIENT-CONTROLLER] - Listing all clients");
        return clientMapper.toResponseList(clientService.findAll());
    }

    @GetMapping("/{id}")
    public ClientResponse findById(@PathVariable Long id) {
        log.info("[CLIENT-CONTROLLER] - Finding client by ID: {}", id);
        return clientMapper.toResponse(clientService.findById(id));
    }
}
