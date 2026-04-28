package br.com.centralplantao.api.controller;

import br.com.centralplantao.api.dto.SlotResponse;
import br.com.centralplantao.api.mapper.SlotMapper;
import br.com.centralplantao.domain.service.SlotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/slots")
@RequiredArgsConstructor
public class SlotController {

    private final SlotService slotService;
    private final SlotMapper slotMapper;

    @GetMapping
    public List<SlotResponse> list(
            @RequestParam Long contractId,
            @RequestParam int year,
            @RequestParam int month) {
        
        YearMonth targetMonth = YearMonth.of(year, month);
        log.info("[SLOT-API] - Querying slots for contract {} in {}", contractId, targetMonth);
        
        return slotMapper.toResponseList(slotService.findByContractAndMonth(contractId, targetMonth));
    }

    @PostMapping("/generate")
    @ResponseStatus(HttpStatus.CREATED)
    public void generate(
            @RequestParam Long contractId,
            @RequestParam int year,
            @RequestParam int month) {
        
        YearMonth targetMonth = YearMonth.of(year, month);
        log.info("[SLOT-API] - Request to generate slots for contract {} in {}", contractId, targetMonth);
        
        slotService.generateMonthlySlots(contractId, targetMonth);
    }
}
