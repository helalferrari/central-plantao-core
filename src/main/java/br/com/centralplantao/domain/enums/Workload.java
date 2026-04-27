package br.com.centralplantao.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Workload {
    W12(12),
    W24(24);

    private final int hours;
}
