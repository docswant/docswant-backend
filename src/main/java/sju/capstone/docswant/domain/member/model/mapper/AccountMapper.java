package sju.capstone.docswant.domain.member.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import sju.capstone.docswant.domain.member.model.dto.AccountDto;
import sju.capstone.docswant.domain.member.model.entity.Account;

@Mapper
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDto.Response toDto(Account account, String accessToken);

}
