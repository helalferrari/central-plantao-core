package br.com.centralplantao.api.controller;

import br.com.centralplantao.api.dto.ContractRequest;
import br.com.centralplantao.api.dto.ContractResponse;
import br.com.centralplantao.api.mapper.ContractMapper;
import br.com.centralplantao.domain.model.Contract;
import br.com.centralplantao.domain.service.ContractService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;
    private final ContractMapper contractMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContractResponse create(@RequestBody @Valid ContractRequest request) {
        log.info("Received request to create a new contract: {}", request.name());
        log.debug("Request payload: {}", request);

        Contract contract = contractMapper.toEntity(request);
        Contract savedContract = contractService.saveContract(contract);
        
        log.info("Contract created successfully with name: {}", request.name());
        return contractMapper.toResponse(savedContract);
    }

    @PutMapping("/{id}")
    public ContractResponse update(@PathVariable Long id, @RequestBody @Valid ContractRequest request) {
        log.info("Received request to update contract ID: {}", id);
        log.debug("Update payload: {}", request);

        Contract contract = contractMapper.toEntity(request);
        Contract updated = contractService.updateContract(id, contract);

        log.info("Contract ID: {} updated successfully", id);
        return contractMapper.toResponse(updated);
    }

    @GetMapping("/{id}")
    public ContractResponse findById(@PathVariable Long id) {
        log.info("Received request to find contract by ID: {}", id);
        Contract contract = contractService.findById(id);
        return contractMapper.toResponse(contract);
    }

    @GetMapping
    public List<ContractResponse> list() {
        log.info("Received request to list all contracts");
        List<Contract> contracts = contractService.findAll();
        return contractMapper.toResponseList(contracts);
    }
}
