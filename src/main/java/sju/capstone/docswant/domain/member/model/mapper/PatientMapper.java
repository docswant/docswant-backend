package sju.capstone.docswant.domain.member.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import sju.capstone.docswant.domain.member.model.dto.PatientDto;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;
import sju.capstone.docswant.domain.rounding.model.entity.Rounding;

@Mapper
public interface PatientMapper {

    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    @Mapping(target = "patientSchedule.hospitalizationDate", source = "hospitalizationDate")
    Patient toEntity(PatientDto.Request requestDto);

    @Mappings({
            @Mapping(target = "hospitalizationDate", source = "patientSchedule.hospitalizationDate"),
            @Mapping(target = "surgeryDate", source = "patientSchedule.surgeryDate"),
            @Mapping(target = "dischargeDate", source = "patientSchedule.dischargeDate")
    })
    PatientDto.Response toDto(Patient patient);

    @Mappings({
            @Mapping(target = "patientName", source = "patient.name"),
            @Mapping(target = "hospitalizationDate", source = "patient.patientSchedule.hospitalizationDate"),
            @Mapping(target = "surgeryDate", source = "patient.patientSchedule.surgeryDate"),
            @Mapping(target = "dischargeDate", source = "patient.patientSchedule.dischargeDate"),
            @Mapping(target = "doctorName", source = "patient.doctor.name"),
            @Mapping(target = "doctorMajor", source = "patient.doctor.major"),
            @Mapping(target = "roundingTime", source = "rounding.roundingSchedule.roundingTime")
    })
    PatientDto.PatientRoundingResponse toPatientRoundingDto(Patient patient, Rounding rounding);

}
