package com.uniadmission.universityadmissions.models.DTO.applicant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateApplicantDTO {
    private String name;
    private String address;
    private String email;
    private Date dateOfBirth;
}
