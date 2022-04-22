package sju.capstone.docswant.domain.member.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import sju.capstone.docswant.domain.member.model.dto.DoctorDto;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface DoctorMapper {

    DoctorDto.Response toDto(Doctor doctor);

    Doctor toEntity(DoctorDto.Request requestDto);

}
