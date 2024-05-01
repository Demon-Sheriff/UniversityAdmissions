package com.uniadmission.universityadmissions.repositories;

import com.uniadmission.universityadmissions.models.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Integer> {
}
