package com.uniadmission.universityadmissions.models.DTO.applicant;

import com.uniadmission.universityadmissions.models.CustomStatus.AppplicationStatus;
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
public class ApplicantDTO {

	private long applicantID;

	private String name;

	private String address;

	private String email;

	private Date dateOfBirth;

	private AppplicationStatus appplicationStatus;

}
