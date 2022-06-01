package sju.capstone.docswant.domain.rounding.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoundingStatus {
    TODO("예정"), DONE("완료");

    private final String message;
}
