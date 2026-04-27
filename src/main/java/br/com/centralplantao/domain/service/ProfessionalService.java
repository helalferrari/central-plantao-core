package br.com.centralplantao.domain.service;

import br.com.centralplantao.domain.exception.ResourceNotFoundException;
import br.com.centralplantao.domain.model.Client;
import br.com.centralplantao.domain.model.Professional;
import br.com.centralplantao.domain.repository.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfessionalService {
    private final ProfessionalRepository professionalRepository;
    private final ClientService clientService;

    @Transactional
    public Professional saveProfessional(Long clientId, Professional professional) {
        log.info("[PROFESSIONAL-SERVICE] - Linking professional {} to client ID: {}", professional.getFullName(), clientId);
        Client client = clientService.findById(clientId);
        professional.setClient(client);
        return professionalRepository.save(professional);
    }

    public List<Professional> findAll() {
        log.info("[PROFESSIONAL-SERVICE] - Fetching all professionals");
        return professionalRepository.findAll();
    }

    public Professional findById(Long id) {
        log.info("[PROFESSIONAL-SERVICE] - Finding professional by ID: {}", id);
        return professionalRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("[PROFESSIONAL-SERVICE] - Professional not found with ID: {}", id);
                    return new ResourceNotFoundException("Professional not found with ID: " + id);
                });
    }
}
