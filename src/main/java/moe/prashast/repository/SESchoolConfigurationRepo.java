package moe.prashast.repository;

import aj.org.objectweb.asm.commons.Remapper;
import moe.prashast.entity.SESchoolConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SESchoolConfigurationRepo extends JpaRepository<SESchoolConfiguration,Long> {
    Optional<SESchoolConfiguration> findBySpecialEducatorIdAndSchoolIdAndYearIdAndIsActive(Long specialEducatorId, Integer schoolId, Short yearId, short i);
}
