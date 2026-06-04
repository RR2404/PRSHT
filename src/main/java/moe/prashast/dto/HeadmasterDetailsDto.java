package moe.prashast.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HeadmasterDetailsDto {

    private Long teacherId;
    private Short yearId;
    private Integer schoolId;

    private String teacherName;

    private Short gender;
    private LocalDate dob;

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
