package com.uniadmission.universityadmissions.models.DTO.program;

import com.uniadmission.universityadmissions.models.CustomStatus.DegreeLevel;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgramDTO {
    private long programID;
    private String name;
    private long departmentID;
    private DegreeLevel degreeLevel;
    private int duration;
}
