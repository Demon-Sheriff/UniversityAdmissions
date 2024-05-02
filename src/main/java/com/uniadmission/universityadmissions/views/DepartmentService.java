package com.uniadmission.universityadmissions.views;

import com.uniadmission.universityadmissions.exceptions.BadRequestException;
import com.uniadmission.universityadmissions.models.DTO.department.CreateDepartmentDTO;
import com.uniadmission.universityadmissions.models.DTO.department.DepartmentDTO;
import com.uniadmission.universityadmissions.models.DTO.department.UpdateDepartmentDTO;

import java.util.List;

public interface DepartmentService {

	List<DepartmentDTO> getAllDepartments();

	DepartmentDTO getDepartmentByID(Long departmentID);

	DepartmentDTO createDepartment(CreateDepartmentDTO createDepartmentDTO) throws BadRequestException;

	DepartmentDTO updateDepartment(Long departmentID, UpdateDepartmentDTO updateDepartmentDTO)
			throws BadRequestException;

	DepartmentDTO deleteDepartment(Long departmentID);

}
