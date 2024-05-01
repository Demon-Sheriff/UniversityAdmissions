package com.uniadmission.universityadmissions.repositories;

import com.uniadmission.universityadmissions.models.ProgramEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramRepository extends JpaRepository<ProgramEntity, Integer> {
    ProgramEntity findByProgramID(Long programID);
}
