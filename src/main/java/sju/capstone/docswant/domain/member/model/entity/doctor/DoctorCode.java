package sju.capstone.docswant.domain.member.model.entity.doctor;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "doctor_code")
@Entity
public class DoctorCode {

    @Id
    private String code;

    //TODO: 인증에 필요한 정보 추후에 추가

    @Builder
    public DoctorCode(String code) {
        this.code = code;
    }
}
