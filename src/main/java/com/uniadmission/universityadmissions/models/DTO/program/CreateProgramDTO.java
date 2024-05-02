package com.uniadmission.universityadmissions.models.DTO.program;

import com.uniadmission.universityadmissions.models.CustomStatus.DegreeLevel;
import com.uniadmission.universityadmissions.models.DepartmentEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProgramDTO {

	private String name;

	private Long department;

	private DegreeLevel degreeLevel;

	private Integer duration;

}
