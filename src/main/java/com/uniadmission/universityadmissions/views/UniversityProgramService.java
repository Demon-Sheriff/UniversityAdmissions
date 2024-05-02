package com.uniadmission.universityadmissions.views;

import com.uniadmission.universityadmissions.exceptions.BadRequestException;
import com.uniadmission.universityadmissions.exceptions.NoProgramFoundException;
import com.uniadmission.universityadmissions.models.DTO.department.DepartmentDTO;
import com.uniadmission.universityadmissions.models.DTO.program.CreateProgramDTO;
import com.uniadmission.universityadmissions.models.DTO.program.ProgramDTO;
import com.uniadmission.universityadmissions.models.DTO.program.UpdateProgramDTO;
import com.uniadmission.universityadmissions.models.DepartmentEntity;
import com.uniadmission.universityadmissions.models.ProgramEntity;
import com.uniadmission.universityadmissions.repositories.ProgramRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UniversityProgramService implements ProgramService {

	private final ProgramRepository programRepository;

	private final DepartmentService departmentService;

	private final ModelMapper modelMapper;

	UniversityProgramService(ProgramRepository programRepository, DepartmentService departmentService) {
		this.programRepository = programRepository;
		this.departmentService = departmentService;
		this.modelMapper = new ModelMapper();
	}

	public List<ProgramDTO> getAllPrograms(Long department) throws NoProgramFoundException {
		if (department != -1) {
			DepartmentDTO departmentDTO = departmentService.getDepartmentByID(department);
			if (departmentDTO == null) {
				throw new NoProgramFoundException("NO SUCH DEPARTMENT WIH ID " + department + ".");
			}
			DepartmentEntity departmentEntity = modelMapper.map(departmentDTO, DepartmentEntity.class);
			List<ProgramEntity> programs = programRepository.findByDepartment(departmentEntity);
			List<ProgramDTO> response = new ArrayList<>();
			for (ProgramEntity program : programs) {
				program.getDepartment().setName(departmentDTO.getName());
				program.getDepartment().setDescription(departmentDTO.getDescription());
				response.add(modelMapper.map(program, ProgramDTO.class));
			}
			return response;
		}
		List<ProgramEntity> programs = programRepository.findAll();
		List<ProgramDTO> response = new ArrayList<>();
		for (ProgramEntity program : programs) {
			DepartmentDTO departmentDTO = departmentService
				.getDepartmentByID(program.getDepartment().getDepartmentID());
			program.getDepartment().setName(departmentDTO.getName());
			program.getDepartment().setDescription(departmentDTO.getDescription());
			response.add(modelMapper.map(program, ProgramDTO.class));
		}
		return response;
	}

	public ProgramDTO getProgramById(Long id) throws NoProgramFoundException {
		ProgramEntity program = programRepository.findByProgramID(id);
		if (program == null) {
			throw new NoProgramFoundException("NO SUCH PROGRAM WIH ID " + id + ".");
		}
		return modelMapper.map(program, ProgramDTO.class);
	}

	public ProgramDTO createProgram(CreateProgramDTO createProgramDTO)
			throws BadRequestException, NoProgramFoundException {
		if (createProgramDTO.getName() == null || createProgramDTO.getDepartment() == 0) {
			StringBuilder message = new StringBuilder("BAD REQUEST :-> ");
			if (createProgramDTO.getName() == null)
				message.append(" PROGRAM NAME NOT FOUND ");
			if (createProgramDTO.getDepartment() == 0)
				message.append(" DEPARTMENT NOT FOUND ");
			throw new BadRequestException(message.toString());
		}
		ProgramEntity program = new ProgramEntity(createProgramDTO);
		DepartmentDTO departmentDTO = departmentService.getDepartmentByID(createProgramDTO.getDepartment());
		if (departmentDTO == null) {
			throw new NoProgramFoundException("NO SUCH DEPARTMENT WIH ID " + createProgramDTO.getDepartment() + ".");
		}
		DepartmentEntity departmentEntity = modelMapper.map(departmentDTO, DepartmentEntity.class);
		program.setDepartment(departmentEntity);
		program = programRepository.save(program);
		return modelMapper.map(program, ProgramDTO.class);
	}

	public ProgramDTO updateProgram(Long programID, UpdateProgramDTO updateProgramDTO)
			throws BadRequestException, NoProgramFoundException {
		if (programID == null) {
			throw new BadRequestException("BAD REQUEST :-> PROGRAM ID NOT FOUND.");
		}
		ProgramEntity program = programRepository.findByProgramID(programID);
		if (program == null) {
			throw new NoProgramFoundException("NO SUCH PROGRAM WIH ID " + programID + ".");
		}
		if (updateProgramDTO.getName() != null)
			program.setName(updateProgramDTO.getName());
		if (updateProgramDTO.getDepartmentID() != null) {
			DepartmentDTO department = departmentService.getDepartmentByID(updateProgramDTO.getDepartmentID());
			if (department == null) {
				throw new NoProgramFoundException(
						"NO SUCH DEPARTMENT WIH ID " + updateProgramDTO.getDepartmentID() + ".");
			}
			DepartmentEntity departmentEntity = modelMapper.map(department, DepartmentEntity.class);
			program.setDepartment(departmentEntity);
		}
		if (updateProgramDTO.getDegreeLevel() != null)
			program.setName(updateProgramDTO.getName());
		if (updateProgramDTO.getDuration() != null)
			program.setDuration(updateProgramDTO.getDuration());
		program = programRepository.save(program);
		return modelMapper.map(program, ProgramDTO.class);
	}

	public ProgramDTO deleteProgram(Long programID) throws NoProgramFoundException {
		ProgramEntity program = programRepository.findByProgramID(programID);
		if (program == null) {
			throw new NoProgramFoundException("NO SUCH PROGRAM WIH ID " + programID + ".");
		}
		programRepository.delete(program);
		return modelMapper.map(program, ProgramDTO.class);
	}

}
