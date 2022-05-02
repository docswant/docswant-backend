package sju.capstone.docswant.domain.member.model.entity.doctor;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum CodeValidity {
    VALID, INVALID
}
