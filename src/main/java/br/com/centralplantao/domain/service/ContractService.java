package br.com.centralplantao.domain.service;

import br.com.centralplantao.domain.model.Contract;
import br.com.centralplantao.domain.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;

    @Transactional
    public Contract saveContract(Contract contract) {
        log.info("[CONTRACT-SERVICE] - Starting contract persistence for: {}", contract.getName());
        log.debug("[CONTRACT-SERVICE] - Contract payload details: {}", contract);

        try {
            validateDates(contract);
            
            if (contract.getContractedShifts() != null) {
                contract.getContractedShifts().forEach(shift -> shift.setContract(contract));
            }
            
            Contract savedContract = contractRepository.save(contract);
            log.info("[CONTRACT-SERVICE] - Contract successfully persisted with ID: {}", savedContract.getId());
            return savedContract;
            
        } catch (Exception e) {
            log.error("[CONTRACT-SERVICE] - Error persisting contract: {}. Reason: {}", contract.getName(), e.getMessage(), e);
            throw e;
        }
    }

    public List<Contract> findAll() {
        log.info("[CONTRACT-SERVICE] - Fetching all contracts");
        return contractRepository.findAll();
    }

    private void validateDates(Contract contract) {
        if (contract.getEndDate().isBefore(contract.getStartDate())) {
            throw new IllegalArgumentException("The end date cannot be earlier than the start date.");
        }
    }
}
