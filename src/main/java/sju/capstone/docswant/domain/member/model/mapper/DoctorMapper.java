package sju.capstone.docswant.domain.member.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import sju.capstone.docswant.domain.member.model.dto.DoctorDto;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;

@Mapper
public interface DoctorMapper {

    DoctorMapper INSTANCE = Mappers.getMapper(DoctorMapper.class);

    DoctorDto.Response toDto(Doctor doctor);

    Doctor toEntity(DoctorDto.Request requestDto);

}
