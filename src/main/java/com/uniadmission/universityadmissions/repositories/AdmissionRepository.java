package com.uniadmission.universityadmissions.repositories;

import com.uniadmission.universityadmissions.models.AdmissionEntity;
import com.uniadmission.universityadmissions.models.ApplicantEntity;
import com.uniadmission.universityadmissions.models.ProgramEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdmissionRepository extends JpaRepository<AdmissionEntity, Long> {

	AdmissionEntity getByAdmissionID(Long Id);

	AdmissionEntity getAdmissionEntityByApplicantAndProgram(ApplicantEntity applicant, ProgramEntity program);

}
