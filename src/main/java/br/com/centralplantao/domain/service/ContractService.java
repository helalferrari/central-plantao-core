package br.com.centralplantao.domain.service;

import br.com.centralplantao.domain.model.Contract;
import br.com.centralplantao.domain.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;

    @Transactional
    public Contract save(Contract contract) {
        validateDates(contract);
        
        if (contract.getContractedShifts() != null) {
            contract.getContractedShifts().forEach(shift -> shift.setContract(contract));
        }
        
        return contractRepository.save(contract);
    }

    public List<Contract> findAll() {
        return contractRepository.findAll();
    }

    private void validateDates(Contract contract) {
        if (contract.getEndDate().isBefore(contract.getStartDate())) {
            throw new IllegalArgumentException("The end date cannot be earlier than the start date.");
        }
    }
}
