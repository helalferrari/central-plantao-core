package br.com.centralplantao.api.controller;

import br.com.centralplantao.api.dto.ContractRequest;
import br.com.centralplantao.api.dto.ContractResponse;
import br.com.centralplantao.api.mapper.ContractMapper;
import br.com.centralplantao.domain.model.Contract;
import br.com.centralplantao.domain.service.ContractService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;
    private final ContractMapper contractMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContractResponse create(@RequestBody @Valid ContractRequest request) {
        Contract contract = contractMapper.toEntity(request);
        Contract savedContract = contractService.save(contract);
        return contractMapper.toResponse(savedContract);
    }

    @GetMapping
    public List<ContractResponse> list() {
        List<Contract> contracts = contractService.findAll();
        return contractMapper.toResponseList(contracts);
    }
}
