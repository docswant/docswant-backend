package sju.capstone.docswant.domain.requiremet.model.mapper;

import org.junit.jupiter.api.Test;
import sju.capstone.docswant.domain.requirement.model.dto.RequirementDto;
import sju.capstone.docswant.domain.requirement.model.entity.Requirement;
import sju.capstone.docswant.domain.requirement.model.mapper.RequirementMapper;

import static org.assertj.core.api.Assertions.assertThat;

class RequirementMapperTest {
    private RequirementMapper mapper = RequirementMapper.INSTANCE;

    @Test
    void 엔티티에서_DTO_테스트() {
        //given
        Requirement requestEntity = Requirement.builder().title("requirement title").content("requirement content").build();

        //when
        RequirementDto.Response resultDto = mapper.toDto(requestEntity);

        //then
        assertThat(resultDto.getId()).isEqualTo(requestEntity.getId());
        assertThat(resultDto.getTitle()).isEqualTo(requestEntity.getTitle());
        assertThat(resultDto.getContent()).isEqualTo(requestEntity.getContent());
        assertThat(resultDto.getStatus()).isEqualTo(requestEntity.getStatus());
    }

    @Test
    void DTO에서_엔티티_테스트() {
        //given
        RequirementDto.Request requestDto = RequirementDto.Request.builder().title("requirement title").content("requirement content").build();

        //when
        Requirement resultEntity = mapper.toEntity(requestDto);

        //then
        assertThat(resultEntity.getTitle()).isEqualTo(requestDto.getTitle());
        assertThat(resultEntity.getContent()).isEqualTo(requestDto.getContent());
    }


}
