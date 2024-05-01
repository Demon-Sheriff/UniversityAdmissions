package com.uniadmission.universityadmissions.models;

import com.uniadmission.universityadmissions.models.CustomStatus.AdmissionStatus;
import com.uniadmission.universityadmissions.models.DTO.admission.CreateAdmissionDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long admissionID;

    @Column(name = "applicant", nullable = false)
    private Long applicant;

    @Column(name = "program", nullable = false)
    private Long program;

    @Column(name = "admissionStatus", nullable = false)
    private AdmissionStatus admissionStatus;

    @Column(name = "decisionDate", nullable = false)
    private Date decisionDate;

    public AdmissionEntity(CreateAdmissionDTO createAdmissionDTO) {
        this.setApplicant(createAdmissionDTO.getApplicant());
        this.setProgram(createAdmissionDTO.getProgram());
        this.setAdmissionStatus(AdmissionStatus.PENDING);
        this.setDecisionDate(new Date());
    }
}