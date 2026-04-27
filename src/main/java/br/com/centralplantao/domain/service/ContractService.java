package br.com.centralplantao.domain.service;

import br.com.centralplantao.domain.exception.ResourceNotFoundException;
import br.com.centralplantao.domain.model.Client;
import br.com.centralplantao.domain.model.Contract;
import br.com.centralplantao.domain.model.ContractedShift;
import br.com.centralplantao.domain.enums.Workload;
import br.com.centralplantao.domain.enums.ScheduleType;
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
    private final ClientService clientService;

    @Transactional
    public Contract saveContract(Long clientId, Contract contract) {
        log.info("[CONTRACT-SERVICE] - Starting contract persistence for client ID: {}", clientId);
        
        Client client = clientService.findById(clientId);
        contract.setClient(client);

        try {
            validateContract(contract);
            
            if (contract.getContractedShifts() != null) {
                contract.getContractedShifts().forEach(shift -> shift.setContract(contract));
            }
            
            Contract savedContract = contractRepository.save(contract);
            log.info("[CONTRACT-SERVICE] - Contract successfully persisted with ID: {}", savedContract.getId());
            return savedContract;
            
        } catch (Exception e) {
            log.error("[CONTRACT-SERVICE] - Error persisting contract. Reason: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public Contract updateContract(Long id, Long clientId, Contract updatedData) {
        log.info("[CONTRACT-SERVICE] - Updating contract. ID: {}", id);
        
        Contract existingContract = findById(id);
        Client client = clientService.findById(clientId);

        try {
            validateContract(updatedData);
            
            existingContract.setDescription(updatedData.getDescription());
            existingContract.setStartDate(updatedData.getStartDate());
            existingContract.setEndDate(updatedData.getEndDate());
            existingContract.setActive(updatedData.isActive());
            existingContract.setClient(client);
            
            // Clear and update shifts
            existingContract.getContractedShifts().clear();
            if (updatedData.getContractedShifts() != null) {
                updatedData.getContractedShifts().forEach(shift -> {
                    shift.setContract(existingContract);
                    existingContract.getContractedShifts().add(shift);
                });
            }
            
            Contract saved = contractRepository.save(existingContract);
            log.info("[CONTRACT-SERVICE] - Contract ID: {} successfully updated", id);
            return saved;
        } catch (Exception e) {
            log.error("[CONTRACT-SERVICE] - Error updating contract ID: {}. Reason: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    public List<Contract> findAll() {
        log.info("[CONTRACT-SERVICE] - Fetching all contracts");
        return contractRepository.findAll();
    }

    public Contract findById(Long id) {
        log.info("[CONTRACT-SERVICE] - Fetching contract for edit. ID: {}", id);
        return contractRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("[CONTRACT-SERVICE] - Contract not found. ID: {}", id);
                    return new ResourceNotFoundException("Contract not found with ID: " + id);
                });
    }

    private void validateContract(Contract contract) {
        if (contract.getEndDate().isBefore(contract.getStartDate())) {
            throw new IllegalArgumentException("The end date cannot be earlier than the start date.");
        }
        
        if (contract.getContractedShifts() != null) {
            contract.getContractedShifts().forEach(this::validateWorkloadCorrelation);
        }
    }

    private void validateWorkloadCorrelation(ContractedShift shift) {
        ScheduleType schedule = shift.getScheduleType();
        Workload workload = shift.getWorkload();
        boolean isValid = false;

        switch (schedule) {
            case SHIFT_24X48 -> isValid = workload == Workload.W24;
            case SHIFT_12X36 -> isValid = workload == Workload.W12;
            case SHIFT_6X1, SHIFT_5X2, SHIFT_4X3 -> isValid = workload == Workload.W8;
        }

        if (!isValid) {
            log.error("[CONTRACT-SERVICE] - Validation violation: Schedule {} is incompatible with Workload {}", schedule, workload);
            throw new IllegalArgumentException(String.format("Schedule %s requires Workload %s", 
                    schedule, getRequiredWorkload(schedule)));
        }
    }

    private String getRequiredWorkload(ScheduleType schedule) {
        return switch (schedule) {
            case SHIFT_24X48 -> "W24";
            case SHIFT_12X36 -> "W12";
            default -> "W8";
        };
    }
}
