package sju.capstone.docswant.domain.member.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import sju.capstone.docswant.domain.member.model.entity.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findByCode(String code);

    Account findByUsername(String username);

    boolean existsByUsername(String username);
}
