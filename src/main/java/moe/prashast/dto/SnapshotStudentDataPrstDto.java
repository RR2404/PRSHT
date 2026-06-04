package moe.prashast.dto;

import lombok.Data;
import moe.prashast.entity.SnapshotStudentDataPrst;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SnapshotStudentDataPrstDto {

    private Short stateId;
    private Integer schoolId;
    private Integer yearId;
    private Integer studentId;

    private String studentPen;
    private String studentName;
    private Short gender;
    private LocalDate studentDob;

    private Short classId;
    private Short sectionId;
    private Short acYearId;

    private String motherName;
    private String fatherName;
    private String guardianName;

    private String mobileNo1;
    private String mobileNo2;
    private String emailId;

    private Short socCatId;
    private Short minorityId;

    private Short isBplYn;
    private Short ewsYn;
    private Short cwsnYn;

    private Integer[] impairmentType;
    private BigDecimal impairmentPercent;

    private Short natIndYn;
    private String admnNumber;
    private Short studentStatus;


    public static SnapshotStudentDataPrstDto convertToDto(SnapshotStudentDataPrst entity) {

        SnapshotStudentDataPrstDto dto = new SnapshotStudentDataPrstDto();
        dto.stateId = entity.getStateId();

        // EmbeddedId values
        if (entity.getId() != null) {
            dto.schoolId = entity.getId().getSchoolId();
            dto.yearId = entity.getId().getYearId();
            dto.studentId = entity.getId().getStudentId();
        }

        dto.studentPen = entity.getStudentPen();
        dto.studentName = entity.getStudentName();
        dto.gender = entity.getGender();
        dto.studentDob = entity.getStudentDob();

        dto.classId = entity.getClassId();
        dto.sectionId = entity.getSectionId();
        dto.acYearId = entity.getAcYearId();

        dto.motherName = entity.getMotherName();
        dto.fatherName = entity.getFatherName();
        dto.guardianName = entity.getGuardianName();

        dto.mobileNo1 = entity.getMobileNo1();
        dto.mobileNo2 = entity.getMobileNo2();
        dto.emailId = entity.getEmailId();

        dto.socCatId = entity.getSocCatId();
        dto.minorityId = entity.getMinorityId();

        dto.isBplYn = entity.getIsBplYn();
        dto.ewsYn = entity.getEwsYn();
        dto.cwsnYn = entity.getCwsnYn();

        dto.impairmentType = entity.getImpairmentType();
        dto.impairmentPercent = entity.getImpairmentPercent();

        dto.natIndYn = entity.getNatIndYn();
        dto.admnNumber = entity.getAdmnNumber();
        dto.studentStatus = entity.getStudentStatus();

        return dto;
    }
}