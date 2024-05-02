package com.uniadmission.universityadmissions.repositories;

import com.uniadmission.universityadmissions.models.AdmissionEntity;
import com.uniadmission.universityadmissions.models.DTO.admission.AdmissionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdmissionRepository extends JpaRepository<AdmissionEntity, Integer> {

	AdmissionEntity getByAdmissionID(Long Id);

}
