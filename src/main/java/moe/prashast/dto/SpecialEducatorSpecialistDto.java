package moe.prashast.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;
@Data
public class SpecialEducatorSpecialistDto {

    private String specialistName;
    private String mobile;
    private String emailId;
    private String gender;
    private LocalDate dateOfBirth;
    private String educatorType;
    private Integer qualificationId;
    private Short experienceYears;
    private String referenceNumber;
    private Short isActive ;

}
