package com.uniadmission.universityadmissions.models.DTO.program;

import com.uniadmission.universityadmissions.models.CustomStatus.DegreeLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProgramDTO {
    private String name;
    private Long departmentID;
    private DegreeLevel degreeLevel;
    private Integer duration;
}
