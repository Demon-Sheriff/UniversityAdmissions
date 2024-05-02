package com.uniadmission.universityadmissions.repositories;

import com.uniadmission.universityadmissions.models.AdmissionEntity;
import com.uniadmission.universityadmissions.models.ApplicantEntity;
import com.uniadmission.universityadmissions.models.DTO.admission.AdmissionDTO;
import com.uniadmission.universityadmissions.models.ProgramEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdmissionRepository extends JpaRepository<AdmissionEntity, Integer> {
	AdmissionEntity getByAdmissionID(Long Id);

	AdmissionEntity getAdmissionEntityByApplicantAndProgram(ApplicantEntity applicant, ProgramEntity program);

}
