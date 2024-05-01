package com.uniadmission.universityadmissions.repositories;

import com.uniadmission.universityadmissions.models.ApplicantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantRepository extends JpaRepository<ApplicantEntity, Integer> {
}
