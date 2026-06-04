package moe.prashast.repository;



import moe.prashast.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SchoolConfigurationRepository
        extends JpaRepository<SchoolConfiguration, SchoolConfigurationId> {

    Optional<SchoolConfiguration> findByIdSchoolIdAndIdYearId(Integer schoolId, Short yearId);

    SchoolConfiguration findByModuleId(Short moduleId);

//    List<SchoolConfiguration> findbyschoolIdInAndYearId(Set<Integer> schoolIds, short i);

//    SchoolConfiguration findBySchoolIdAndYearId(Integer schoolId, short i);
}

