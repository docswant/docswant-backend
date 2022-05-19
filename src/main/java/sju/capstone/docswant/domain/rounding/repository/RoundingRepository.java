package sju.capstone.docswant.domain.rounding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sju.capstone.docswant.domain.rounding.model.entity.Rounding;

public interface RoundingRepository extends JpaRepository<Rounding, Long>, RoundingRepositoryCustom {
    
}
