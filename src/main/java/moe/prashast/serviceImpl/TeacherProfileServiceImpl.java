package moe.prashast.serviceImpl;

import lombok.RequiredArgsConstructor;

import moe.prashast.bean.SchoolIdRequestBean;
import moe.prashast.dto.TeacherProfileDto;
import moe.prashast.entity.SchoolConfiguration;
import moe.prashast.entity.TeacherProfileCore;
import moe.prashast.entity.TeacherProfilePrst;
import moe.prashast.repository.SchoolConfigurationRepository;
import moe.prashast.repository.TeacherProfileCoreRepository;
import moe.prashast.repository.TeacherProfilePrstRepository;
import moe.prashast.service.TeacherProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherProfileServiceImpl implements TeacherProfileService {

    @Autowired
    private TeacherProfilePrstRepository tchProfilePrstRepo;
    @Autowired
    private SchoolConfigurationRepository schoolConfigurationRepository;

    @Autowired
    private TeacherProfileCoreRepository tchProfileCoreRepository;

    @Override
    public List<TeacherProfileDto> getTeachersBySchoolIdAndYearId(SchoolIdRequestBean request) {

        List<TeacherProfileDto> result = new ArrayList<>();

        Optional<SchoolConfiguration> schoolConfiguration = schoolConfigurationRepository
                .findByIdSchoolIdAndIdYearId(request.getSchoolId(), request.getYearId());

        if (schoolConfiguration.isEmpty()) {
            List<TeacherProfileCore> teachers = tchProfileCoreRepository.findBySchoolId(request.getSchoolId());

            for (TeacherProfileCore entity : teachers) {

                if (entity == null) {
                    continue;
                }

                TeacherProfileDto dto = new TeacherProfileDto();
                dto.setEmpStaffId(entity.getId().getEmpStaffId());
                dto.setSchoolId(entity.getSchoolId());
                dto.setYearId(entity.getId().getYearId());
                dto.setNatTeacherId(entity.getNatTeacherId());
                dto.setTchName(entity.getTchName());
                dto.setGender(entity.getGender());
                dto.setDob(entity.getDob());
                dto.setSocialCat(entity.getSocialCat());
                dto.setQualAcad(entity.getQualAcad());
                dto.setMobile(entity.getMobile());
                dto.setEmail(entity.getEmail());
                dto.setNatureOfAppt(entity.getNatureOfAppt());
                dto.setTchType(entity.getTchType());
                dto.setDojService(entity.getDojService());
                dto.setClassTaught(entity.getClassTaught());
                dto.setTrainedCwsn(entity.getTrainedCwsn());
                dto.setTrainedComp(entity.getTrainedComp());
                dto.setIsAssigned(entity.getIsAssigned());

                result.add(dto);
            }


        }
        else{

        Integer moduleId = Integer.valueOf(schoolConfiguration.get().getModuleId());

         if (moduleId >= 1) {

             List<TeacherProfilePrst> teachers = tchProfilePrstRepo.findBySchoolId(request.getSchoolId());

             for (TeacherProfilePrst entity : teachers) {

                 if (entity == null) {
                     continue;
                 }

                 TeacherProfileDto dto = new TeacherProfileDto();

                 dto.setEmpStaffId(entity.getEmpStaffId());
                 dto.setSchoolId(entity.getSchoolId());
                 dto.setYearId(entity.getYearId());
                 dto.setNatTeacherId(entity.getNatTeacherId());
                 dto.setTchName(entity.getTchName());
                 dto.setGender(entity.getGender());
                 dto.setDob(entity.getDob());
                 dto.setSocialCat(entity.getSocialCat());
                 dto.setQualAcad(entity.getQualAcad());
                 dto.setMobile(entity.getMobile());
                 dto.setEmail(entity.getEmail());
                 dto.setNatureOfAppt(entity.getNatureOfAppt());
                 dto.setTchType(entity.getTchType());
                 dto.setDojService(entity.getDojService());
                 dto.setClassTaught(entity.getClassTaught());
                 dto.setTrainedCwsn(entity.getTrainedCwsn());
                 dto.setTrainedComp(entity.getTrainedComp());
                 dto.setIsAssigned(entity.getIsAssigned());

                 result.add(dto);
             }
         }

        }

        return result;
    }

    @Override
    public TeacherProfileDto getTeachersByTeacherId(Long teacherId) {

        return tchProfilePrstRepo.findByEmpStaffId(teacherId)
                .map(entity -> {
                    TeacherProfileDto dto = new TeacherProfileDto();

                    dto.setEmpStaffId(entity.getEmpStaffId());
                    dto.setSchoolId(entity.getSchoolId());
                    dto.setYearId(entity.getYearId());
                    dto.setNatTeacherId(entity.getNatTeacherId());
                    dto.setTchName(entity.getTchName());
                    dto.setGender(entity.getGender());
                    dto.setDob(entity.getDob());
                    dto.setSocialCat(entity.getSocialCat());
                    dto.setQualAcad(entity.getQualAcad());
                    dto.setMobile(entity.getMobile());
                    dto.setEmail(entity.getEmail());
                    dto.setNatureOfAppt(entity.getNatureOfAppt());
                    dto.setTchType(entity.getTchType());
                    dto.setDojService(entity.getDojService());
                    dto.setClassTaught(entity.getClassTaught());
                    dto.setTrainedCwsn(entity.getTrainedCwsn());
                    dto.setTrainedComp(entity.getTrainedComp());
                    dto.setIsAssigned(entity.getIsAssigned());

                    return dto;
                })
                .orElse(null); // or throw exception
    }
}
