package moe.prashast.repository;

import moe.prashast.entity.SpecialEducatorSpecialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecialEducatorSpecialistRepo extends JpaRepository<SpecialEducatorSpecialist,Long> {
    Optional<SpecialEducatorSpecialist> findByMobile(String mobile);
}
