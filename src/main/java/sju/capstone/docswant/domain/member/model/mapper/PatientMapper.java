package sju.capstone.docswant.domain.member.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import sju.capstone.docswant.domain.member.model.dto.PatientDto;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;

@Mapper
public interface PatientMapper {

    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    @Mappings({
            @Mapping(target = "patientSchedule.hospitalizationDate", source = "hospitalizationDate"),
            @Mapping(target = "patientSchedule.surgeryDate", source = "surgeryDate"),
            @Mapping(target = "patientSchedule.dischargeDate", source = "dischargeDate")
    })
    Patient toEntity(PatientDto.Request requestDto);

    @Mappings({
            @Mapping(target = "hospitalizationDate", source = "patientSchedule.hospitalizationDate"),
            @Mapping(target = "surgeryDate", source = "patientSchedule.surgeryDate"),
            @Mapping(target = "dischargeDate", source = "patientSchedule.dischargeDate")
    })
    PatientDto.Response toDto(Patient patient);

}
