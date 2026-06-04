package moe.prashast.serviceImpl;

import moe.prashast.dto.SchoolMasterLiveDto;
import moe.prashast.entity.SchoolMasterLive;
import moe.prashast.repository.SchoolMasterLiveRepository;
import moe.prashast.service.SchoolMasterLiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class SchoolMasterLiveServiceImpl implements SchoolMasterLiveService {

    @Autowired
    SchoolMasterLiveRepository schoolMasterLiveRepository;

    @Override
    public SchoolMasterLiveDto fetchAllBySchoolId(Integer schoolId) {

        SchoolMasterLive e = schoolMasterLiveRepository.findBySchoolId(schoolId).stream().findFirst().orElse(null);

        if (e == null) {
            return null; // or throw custom exception
        }

        SchoolMasterLiveDto d = new SchoolMasterLiveDto();

        d.setSchoolId(e.getSchoolId());
        d.setYearId(e.getYearId());
        d.setUdiseSchCode(e.getUdiseSchCode());
        d.setSchoolName(e.getSchoolName());
        d.setStateCd(e.getStateCd());
        d.setDistrictCd(e.getDistrictCd());
        d.setBlockCd(e.getBlockCd());
        d.setClusterCd(e.getClusterCd());
        d.setStateId(e.getStateId());
        d.setDistrictId(e.getDistrictId());
        d.setBlockId(e.getBlockId());
        d.setClusterId(e.getClusterId());
        d.setSchCategoryId(e.getSchCategoryId());
        d.setSchType(e.getSchType());
        d.setSchMgmtId(e.getSchMgmtId());
        d.setSchMgmtCenterId(e.getSchMgmtCenterId());
        d.setClassFrm(e.getClassFrm());
        d.setClassTo(e.getClassTo());
        d.setPpsecYn(e.getPpsecYn());
        d.setPpsecClsFrm(e.getPpsecClsFrm());
        d.setSchoolStatus(e.getSchoolStatus());
        d.setIsCwsn(e.getIsCwsn());
        d.setHeadMasterName(e.getHeadMasterName());
        d.setHeadMstType(e.getHeadMstType());
        d.setHmMobile(e.getHmMobile());
        d.setSessionStartDate(e.getSessionStartDate());
        d.setSessionEndDate(e.getSessionEndDate());
        d.setOrgStateId(e.getOrgStateId());
        d.setOrgDistrictId(e.getOrgDistrictId());
        d.setOrgBlockId(e.getOrgBlockId());
        d.setPinCode(e.getPinCode());
        d.setAddress(e.getAddress());
        d.setTotalClasses(e.getTotalClasses());
        d.setTotalSections(e.getTotalSections());
        d.setTotalTeachers(e.getTotalTeachers());
        d.setTotalStudents(e.getTotalStudents());
        d.setDataImportedAt(e.getDataImported());
        d.setLocationType(e.getLocationType());
        d.setEmailId(e.getEmailId());


        return d;
    }

}
