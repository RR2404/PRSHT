package moe.prashast.serviceImpl;

import lombok.RequiredArgsConstructor;
import moe.prashast.dto.SchoolClassSectionDetailsDto;
import moe.prashast.dto.SchoolClassSectionSummaryDto;
import moe.prashast.repository.SchoolClassSectionRepository;
import moe.prashast.service.SchoolClassSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolClassSectionServiceImpl
        implements SchoolClassSectionService {

    @Autowired
    private SchoolClassSectionRepository repository;

    // ================= SUMMARY =================
    @Override
    public List<SchoolClassSectionSummaryDto> getSummary(
            Integer yearId,
            String userId,
            Integer roleId,
            Integer stateId,
            Integer schoolId) {

        return repository.getSummary(
                yearId,
                userId,
                roleId,
                stateId,
                schoolId
        );
    }

    // ================= DETAILS =================
    @Override
    public List<SchoolClassSectionDetailsDto> getDetails(
            Integer yearId,
            String userId,
            Integer roleId,
            Integer stateId,
            Integer schoolId) {

        return repository.getDetails(
                yearId,
                userId,
                roleId,
                stateId,
                schoolId
        );
    }
}
