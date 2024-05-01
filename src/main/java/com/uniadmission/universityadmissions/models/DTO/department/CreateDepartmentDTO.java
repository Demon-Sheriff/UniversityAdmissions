package com.uniadmission.universityadmissions.models.DTO.department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDepartmentDTO {
    private String name;
    private String description;
}
