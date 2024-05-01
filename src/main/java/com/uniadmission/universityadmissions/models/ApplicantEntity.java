package com.uniadmission.universityadmissions.models;

import com.uniadmission.universityadmissions.models.CustomStatus.AppplicationStatus;
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

}
