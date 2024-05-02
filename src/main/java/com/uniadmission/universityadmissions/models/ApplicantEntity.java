package com.uniadmission.universityadmissions.models;

import com.uniadmission.universityadmissions.models.CustomStatus.AdmissionStatus;
import com.uniadmission.universityadmissions.models.CustomStatus.AppplicationStatus;
import com.uniadmission.universityadmissions.models.DTO.admission.CreateAdmissionDTO;
import com.uniadmission.universityadmissions.models.DTO.applicant.CreateApplicantDTO;
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
public class ApplicantEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "applicant_id", nullable = false)
	private long applicantID;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "dob", nullable = false)
	private Date dateOfBirth;

	@Column(name = "applicationStatus", nullable = false)
	private AppplicationStatus appplicationStatus;

	public ApplicantEntity(CreateApplicantDTO createApplicantDTO) {
		this.setName(createApplicantDTO.getName());
		this.setEmail(createApplicantDTO.getEmail());
		this.setAddress(createApplicantDTO.getAddress());
		this.setDateOfBirth(createApplicantDTO.getDateOfBirth());
		this.setAppplicationStatus(AppplicationStatus.PENDING);
	}

}
