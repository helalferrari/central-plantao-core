package br.com.centralplantao.domain.service;

import br.com.centralplantao.domain.model.Client;
import br.com.centralplantao.domain.model.Contract;
import br.com.centralplantao.domain.model.ContractedShift;
import br.com.centralplantao.domain.repository.ContractRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContractServiceTest {

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ContractService contractService;

    @Test
    @DisplayName("Should save a contract successfully when data is valid")
    void shouldSaveContractSuccessfully() {
        // Arrange
        Contract contract = Contract.builder()
                .description("Test Contract")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(1))
                .active(true)
                .contractedShifts(List.of(ContractedShift.builder()
                        .sectorDescription("ICU")
                        .scheduleType(br.com.centralplantao.domain.enums.ScheduleType.SHIFT_12X36)
                        .workload(br.com.centralplantao.domain.enums.Workload.W12)
                        .build()))
                .build();

        when(clientService.findById(1L)).thenReturn(new Client());
        when(contractRepository.save(any(Contract.class))).thenReturn(contract);

        // Act
        Contract savedContract = contractService.saveContract(1L, contract);

        // Assert
        assertThat(savedContract).isNotNull();
        assertThat(savedContract.getDescription()).isEqualTo("Test Contract");
        verify(contractRepository, times(1)).save(contract);
    }

    @Test
    @DisplayName("Should throw exception when end date is earlier than start date")
    void shouldThrowExceptionWhenEndDateIsBeforeStartDate() {
        // Arrange
        Contract contract = Contract.builder()
                .description("Invalid Contract")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().minusDays(1))
                .build();

        when(clientService.findById(1L)).thenReturn(new Client());

        // Act & Assert
        assertThatThrownBy(() -> contractService.saveContract(1L, contract))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The end date cannot be earlier than the start date.");

        verify(contractRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should list all contracts")
    void shouldListAllContracts() {
        // Arrange
        when(contractRepository.findAll()).thenReturn(List.of(new Contract(), new Contract()));

        // Act
        List<Contract> contracts = contractService.findAll();

        // Assert
        assertThat(contracts).hasSize(2);
        verify(contractRepository, times(1)).findAll();
    }
}
