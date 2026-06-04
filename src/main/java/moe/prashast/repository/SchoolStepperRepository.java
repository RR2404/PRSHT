package moe.prashast.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import moe.prashast.request.pojo.SchoolConfigRequest;
import org.springframework.stereotype.Repository;

@Repository
public class SchoolStepperRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public String getSchoolConfigurationStepper(SchoolConfigRequest request) {

        Query query = entityManager.createNativeQuery(
                "SELECT  public.get_school_configuration_stepper(?,?,?,?)::text");

        query.setParameter(1, request.getYearId());
        query.setParameter(2, request.getUserId());
        query.setParameter(3, request.getRoleId());
        query.setParameter(4, request.getSchoolId());

        return query.getSingleResult().toString();
    }

}
