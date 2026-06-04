package moe.prashast.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import moe.prashast.dto.UdiseSchoolData;
import moe.prashast.entity.SchoolMasterLiveCore;
import moe.prashast.entity.SchoolMasterLivePrst;
import moe.prashast.repository.SchoolMasterLiveCoreRepository;
import moe.prashast.repository.SchoolMasterLivePrstRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolMasterLiveCoreRepository coreRepo;
    private final SchoolMasterLivePrstRepo prstRepo;

    @Transactional
    public void updateAndCopy(UdiseSchoolData data) {

        Optional<SchoolMasterLiveCore> optionalSchool =coreRepo.findByUdiseSchCode(data.getUdiseCode());

        if (optionalSchool.isPresent()) {

            SchoolMasterLiveCore school = optionalSchool.get();

            school.setSchoolName(data.getSchoolName());
//            school.setStateCd(data.getEduStateCode());
//            school.setDistrictCd(data.getEduDistrictCode());
//            school.setBlockCd(data.getEduBlockCode());
//            school.setClusterCd(data.getEduClusterCode());

//            school.setStateId(data.getLgdStateId().shortValue());
//            school.setDistrictId(data.getLgdDistrictId().shortValue());
//            school.setBlockId(data.getLgdBlockId());
//          school.setClusterId(data.getEduClusterCode() != null ?Integer.parseInt(data.getEduClusterCode()) : null);

            school.setSchCategoryId( data.getSchCategoryId() != null ?data.getSchCategoryId().shortValue() : 0);

            school.setSchType(data.getSchTypeId() != null ?data.getSchTypeId().shortValue() : 0);

            school.setSchMgmtId(data.getSchMgmtId() != null ?data.getSchMgmtId().shortValue() : 0);

            school.setSchMgmtCenterId(data.getSchMgmtCenterId() != null ?data.getSchMgmtCenterId().shortValue() : null);

            school.setClassFrm(data.getLowestClass() != null ?data.getLowestClass().shortValue() : 0);

            school.setClassTo(data.getHighestClass() != null ?data.getHighestClass().shortValue() : 0);

            school.setPpsecYn(data.getPrePrimaryAvailability() != null ?data.getPrePrimaryAvailability().shortValue() : 0);

            school.setPpsecClsFrm(data.getPrePrimaryClassFrom() != null ?data.getPrePrimaryClassFrom().shortValue() : null);

            school.setHeadMasterName(data.getHosName());
//            school.setHmMobile(data.getHosMobile());
            school.setPinCode(data.getPinCode());

            school.setSchoolStatus( data.getSchStatusId() != null ?data.getSchStatusId().shortValue() : 0);


            school.setModifiedTime(LocalDateTime.now());
            school.setModifiedBy(data.getUdiseCode());
            school.setAddress(data.getAddress());
            school.setEmailId(data.getEmail());

            coreRepo.save(school);

            // ---- COPY TO PRST TABLE ----
            Optional<SchoolMasterLivePrst> existingPrst = prstRepo.findByUdiseSchCode(school.getUdiseSchCode());

            if (existingPrst.isEmpty()) {

                SchoolMasterLivePrst prst = new SchoolMasterLivePrst();
                BeanUtils.copyProperties(school, prst);
                prstRepo.save(prst);

            }
        }
    }

}
