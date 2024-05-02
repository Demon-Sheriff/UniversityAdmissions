package com.uniadmission.universityadmissions.models;

import com.uniadmission.universityadmissions.models.CustomStatus.DegreeLevel;
import com.uniadmission.universityadmissions.models.DTO.program.CreateProgramDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgramEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "program_id")
	private Long programID;

	@Column(name = "name", nullable = false)
	private String name;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "department_id", nullable = false, referencedColumnName = "department_id")
	private DepartmentEntity department;

	@Column(name = "degreeLevel", nullable = false)
	private DegreeLevel degreeLevel;

	@Column(name = "duration", nullable = false)
	private int duration;

	public ProgramEntity(CreateProgramDTO createProgramDTO) {
		this.setName(createProgramDTO.getName());
		this.setDegreeLevel(createProgramDTO.getDegreeLevel());
		this.setDuration(createProgramDTO.getDuration());
	}

}
