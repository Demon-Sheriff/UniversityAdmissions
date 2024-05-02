package com.uniadmission.universityadmissions.models.DTO.admission;

import com.uniadmission.universityadmissions.models.CustomStatus.AdmissionStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAdmissionStatusDTO {

	private AdmissionStatus admissionStatus;

}
