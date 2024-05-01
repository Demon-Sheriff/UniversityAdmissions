package com.uniadmission.universityadmissions.models.DTO.program;

import com.uniadmission.universityadmissions.models.CustomStatus.DegreeLevel;

public class UpdateProgramDTO {
    private String name;
    private long departmentID;
    private DegreeLevel degreeLevel;
    private int duration;
}
