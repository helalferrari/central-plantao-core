package br.com.centralplantao.api.controller;

import br.com.centralplantao.api.dto.ProfessionalRequest;
import br.com.centralplantao.api.dto.ProfessionalResponse;
import br.com.centralplantao.api.mapper.ProfessionalMapper;
import br.com.centralplantao.domain.model.Professional;
import br.com.centralplantao.domain.service.ProfessionalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/professionals")
@RequiredArgsConstructor
public class ProfessionalController {
    private final ProfessionalService professionalService;
    private final ProfessionalMapper professionalMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfessionalResponse create(@RequestBody @Valid ProfessionalRequest request) {
        log.info("[PROFESSIONAL-CONTROLLER] - Received request to create professional: {}", request.fullName());
        Professional professional = professionalMapper.toEntity(request);
        Professional saved = professionalService.saveProfessional(request.clientId(), professional);
        return professionalMapper.toResponse(saved);
    }

    @GetMapping
    public List<ProfessionalResponse> list() {
        log.info("[PROFESSIONAL-CONTROLLER] - Listing all professionals");
        return professionalMapper.toResponseList(professionalService.findAll());
    }

    @GetMapping("/{id}")
    public ProfessionalResponse findById(@PathVariable Long id) {
        log.info("[PROFESSIONAL-CONTROLLER] - Finding professional by ID: {}", id);
        return professionalMapper.toResponse(professionalService.findById(id));
    }
}
