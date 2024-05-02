package com.uniadmission.universityadmissions.models.DTO.admission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdmissionDTO {

	@NotNull
	private Long applicant;

	@NotNull
	private Long program;

	@Override
	public String toString() {
		return "CreateAdmissionDTO{" + "applicant=" + applicant + ", program=" + program + '}';
	}

}
