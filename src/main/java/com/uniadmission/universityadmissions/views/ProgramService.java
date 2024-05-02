package com.uniadmission.universityadmissions.views;

import com.uniadmission.universityadmissions.exceptions.BadRequestException;
import com.uniadmission.universityadmissions.exceptions.NoProgramFoundException;
import com.uniadmission.universityadmissions.models.DTO.program.CreateProgramDTO;
import com.uniadmission.universityadmissions.models.DTO.program.ProgramDTO;
import com.uniadmission.universityadmissions.models.DTO.program.UpdateProgramDTO;

import java.util.List;

public interface ProgramService {

	List<ProgramDTO> getAllPrograms(Long department) throws NoProgramFoundException;

	ProgramDTO getProgramById(Long id) throws NoProgramFoundException;

	ProgramDTO createProgram(CreateProgramDTO createProgramDTO) throws BadRequestException, NoProgramFoundException;

	ProgramDTO updateProgram(Long programID, UpdateProgramDTO updateProgramDTO)
			throws BadRequestException, NoProgramFoundException;

	ProgramDTO deleteProgram(Long programID) throws NoProgramFoundException;

}
