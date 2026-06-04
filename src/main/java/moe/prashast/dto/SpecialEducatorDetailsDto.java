package moe.prashast.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class SpecialEducatorDetailsDto {

    private Long empStaffId;
    private Integer schoolId;
    private Short yearId;
    private String natTeacherId;
    private String tchName;
    private Short gender;
    private LocalDate dob;
    private Short socialCat;
    private Short qualAcad;
    private String mobile;
    private String email;
    private Short natureOfAppt;
    private Short tchType;
    private LocalDate dojService;
    private Short classTaught;
    private Short trainedCwsn;
    private Short trainedComp;
    private Short isAssigned;
}
