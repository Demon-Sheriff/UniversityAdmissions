package com.uniadmission.universityadmissions.models.DTO.admission;

import com.uniadmission.universityadmissions.models.CustomStatus.AdmissionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionDTO {
    private Long admissionID;
    private Long applicant;
    private Long program;
    private AdmissionStatus admissionStatus;
    private Date decisionDate;
}