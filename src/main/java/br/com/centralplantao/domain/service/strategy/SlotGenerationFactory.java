package br.com.centralplantao.domain.service.strategy;

import br.com.centralplantao.domain.enums.ScheduleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SlotGenerationFactory {

    private final Shift12x36Generator shift12x36Generator;
    private final FixedDayGenerator fixedDayGenerator;

    public SlotGenerationStrategy getStrategy(ScheduleType scheduleType) {
        if (scheduleType == ScheduleType.SHIFT_12X36 || scheduleType == ScheduleType.SHIFT_24X48) {
            return shift12x36Generator;
        }
        return fixedDayGenerator;
    }
}
