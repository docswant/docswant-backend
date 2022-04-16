package sju.capstone.docswant.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sju.capstone.docswant.domain.member.model.entity.Account;

public interface AccountRepository extends JpaRepository<Account, String> {

    Account findByUsername(String username);

}
