package com.uniadmission.universityadmissions.models.DTO.department;

import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDepartmentDTO {
    @NonNull
    private String name;
    @NonNull
    private String description;
}
