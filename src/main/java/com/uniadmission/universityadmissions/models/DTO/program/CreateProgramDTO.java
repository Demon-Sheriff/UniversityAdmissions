package com.uniadmission.universityadmissions.models.DTO.program;

import com.uniadmission.universityadmissions.models.CustomStatus.DegreeLevel;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProgramDTO {
    @NonNull
    private String name;
    @NonNull
    private Long departmentID;
    @NonNull
    private DegreeLevel degreeLevel;
    @NonNull
    private Integer duration;
}
