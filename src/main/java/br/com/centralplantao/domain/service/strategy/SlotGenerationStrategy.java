package br.com.centralplantao.domain.service.strategy;

import br.com.centralplantao.domain.model.ContractedShift;
import br.com.centralplantao.domain.model.DutySlot;

import java.time.YearMonth;
import java.util.List;

public interface SlotGenerationStrategy {
    List<DutySlot> generate(ContractedShift shift, YearMonth targetMonth);
}
