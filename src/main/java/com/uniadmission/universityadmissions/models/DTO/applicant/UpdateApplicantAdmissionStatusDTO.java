package com.uniadmission.universityadmissions.models.DTO.applicant;

import com.uniadmission.universityadmissions.models.CustomStatus.AppplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateApplicantAdmissionStatusDTO {
    private AppplicationStatus appplicationStatus;
}
