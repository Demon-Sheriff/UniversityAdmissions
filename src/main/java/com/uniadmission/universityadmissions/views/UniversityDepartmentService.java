package com.uniadmission.universityadmissions.views;

import com.uniadmission.universityadmissions.exceptions.BadRequestException;
import com.uniadmission.universityadmissions.exceptions.departmentExceptions.NoDepartmentFoundException;
import com.uniadmission.universityadmissions.models.DTO.department.CreateDepartmentDTO;
import com.uniadmission.universityadmissions.models.DTO.department.DepartmentDTO;
import com.uniadmission.universityadmissions.models.DTO.department.UpdateDepartmentDTO;
import com.uniadmission.universityadmissions.models.DepartmentEntity;
import com.uniadmission.universityadmissions.repositories.DepartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UniversityDepartmentService implements DepartmentService {

	private final DepartmentRepository departmentRepository;

	private final ModelMapper modelMapper;

	public UniversityDepartmentService(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
		this.modelMapper = new ModelMapper();
	}

	public List<DepartmentDTO> getAllDepartments() {
		List<DepartmentEntity> departments = departmentRepository.findAll();
		List<DepartmentDTO> response = new ArrayList<>();
		for (DepartmentEntity department : departments) {
			response.add(modelMapper.map(department, DepartmentDTO.class));
		}
		return response;
	}

	public DepartmentDTO getDepartmentByID(Long departmentID) throws NoDepartmentFoundException {
		DepartmentEntity department = departmentRepository.findByDepartmentID(departmentID);
		if (department == null) {
			throw new NoDepartmentFoundException("NO SUCH DEPARTMENT WITH ID " + departmentID + ".");
		}
		return modelMapper.map(department, DepartmentDTO.class);
	}

	public DepartmentDTO createDepartment(CreateDepartmentDTO createDepartmentDTO) throws BadRequestException {
		if (createDepartmentDTO.getName() == null || createDepartmentDTO.getDescription() == null) {
			StringBuilder message = new StringBuilder("BAD REQUEST :-> ");
			if (createDepartmentDTO.getName() == null)
				message.append(" DEPARTMENT NAME NOT FOUND ");
			if (createDepartmentDTO.getDescription() == null)
				message.append(" DEPARTMENT DESCRIPTION NOT FOUND ");
			throw new BadRequestException(message.toString());
		}
		DepartmentEntity department = new DepartmentEntity(createDepartmentDTO);
		department = departmentRepository.save(department);
		return modelMapper.map(department, DepartmentDTO.class);
	}

	public DepartmentDTO updateDepartment(Long departmentID, UpdateDepartmentDTO updateDepartmentDTO)
			throws BadRequestException {
		// DepartmentDTO department = new DepartmentDTO();
		DepartmentEntity currentDepartment = modelMapper.map(getDepartmentByID(departmentID), DepartmentEntity.class);
		if (updateDepartmentDTO.getName() != null) {
			currentDepartment.setName(updateDepartmentDTO.getName());
		}
		if (updateDepartmentDTO.getDescription() != null) {
			currentDepartment.setDescription(updateDepartmentDTO.getDescription());
		}
		currentDepartment = departmentRepository.save(currentDepartment);
		return modelMapper.map(currentDepartment, DepartmentDTO.class);
	}

	public DepartmentDTO deleteDepartment(Long departmentID) throws BadRequestException, NoDepartmentFoundException {
		if (departmentID == null)
			throw new BadRequestException("BAD REQUEST :-> DEPARTMENT ID NOT FOUND");
		DepartmentDTO departmentDTO = getDepartmentByID(departmentID);
		if (departmentDTO == null) {
			throw new NoDepartmentFoundException("NO SUCH DEPARTMENT WITH WITH GIVEN ID " + departmentID);
		}
		// map the DTO to the entity class
		DepartmentEntity department = modelMapper.map(departmentDTO, DepartmentEntity.class);
		departmentRepository.delete(department);
		return modelMapper.map(department, DepartmentDTO.class);
	}

}
