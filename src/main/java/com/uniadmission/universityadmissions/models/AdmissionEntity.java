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
@AllArgsConstructor
public class AdmissionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "admission_id", nullable = false)
	private Long admissionID;

	@ManyToOne
	@JoinColumn(name = "applicant_id", nullable = false, referencedColumnName = "applicant_id")
	private ApplicantEntity applicant;

	@ManyToOne
	@JoinColumn(name = "program_id", nullable = false, referencedColumnName = "program_id")
	private ProgramEntity program;

	@Column(name = "admissionStatus", nullable = false)
	private AdmissionStatus admissionStatus;

	@Column(name = "decisionDate", nullable = false)
	private Date decisionDate;

	public AdmissionEntity() {
		this.setAdmissionStatus(AdmissionStatus.PENDING);
		this.setDecisionDate(new Date());
	}

}