package com.uniadmission.universityadmissions.views;

import com.uniadmission.universityadmissions.exceptions.BadRequestException;
import com.uniadmission.universityadmissions.exceptions.NoProgramFoundException;
import com.uniadmission.universityadmissions.models.DTO.program.CreateProgramDTO;
import com.uniadmission.universityadmissions.models.DTO.program.ProgramDTO;
import com.uniadmission.universityadmissions.models.ProgramEntity;
import com.uniadmission.universityadmissions.repositories.ProgramRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProgramService {
    private final ProgramRepository programRepository;
    private final ModelMapper modelMapper;
    ProgramService(ProgramRepository programRepository) {
        this.programRepository = programRepository;
        this.modelMapper = new ModelMapper();
    }

    public List<ProgramDTO> getAllPrograms(){
        List<ProgramEntity> programs = programRepository.findAll();
        List<ProgramDTO> response = new ArrayList<>();
        for (ProgramEntity program : programs) {
            response.add(modelMapper.map(program, ProgramDTO.class));
        }
        return response;
    }

    public ProgramDTO getProgramById(Long id) throws NoProgramFoundException {
        ProgramEntity program = programRepository.getByProgramID(id);
        if (program == null) {
            throw new NoProgramFoundException("NO SUCH PROGRAM WIH ID "+id+".");
        }
        return modelMapper.map(program, ProgramDTO.class);
    }

    public ProgramDTO createProgram(CreateProgramDTO createProgramDTO) throws BadRequestException {
        if(createProgramDTO.getName() == null || createProgramDTO.getDepartmentID() == 0) {
            StringBuilder message = new StringBuilder("BAD REQUEST :-> ");
            if(createProgramDTO.getName() == null) message.append(" PROGRAM NAME NOT FOUND ");
            if (createProgramDTO.getDepartmentID() == 0) message.append(" DEPARTMENT NOT FOUND ");
            throw new BadRequestException(message.toString());
        }
        ProgramEntity program = new ProgramEntity(createProgramDTO);
        program = programRepository.save(program);
        return modelMapper.map(program, ProgramDTO.class);
    }

    public ProgramDTO updateProgram(Long programID, CreateProgramDTO createProgramDTO) throws BadRequestException{
        if (programID == null || createProgramDTO.getName() == null || createProgramDTO.getDepartmentID() == 0) {
            StringBuilder message = new StringBuilder("BAD REQUEST :-> ");
            if(programID == null) message.append(" PROGRAM ID NOT FOUND ");
            if(createProgramDTO.getName() == null) message.append(" PROGRAM NAME NOT FOUND ");
            if (createProgramDTO.getDepartmentID() == 0) message.append(" DEPARTMENT NOT FOUND ");
            throw new BadRequestException(message.toString());
        }
        ProgramEntity program = programRepository.getByProgramID(programID);
        program.setName(createProgramDTO.getName());
        program.setDepartmentID(createProgramDTO.getDepartmentID());
        program.setDegreeLevel(createProgramDTO.getDegreeLevel());
        program.setDuration(createProgramDTO.getDuration());
        program = programRepository.save(program);
        return modelMapper.map(program, ProgramDTO.class);
    }

    public ProgramDTO deleteProgram(Long programID) throws NoProgramFoundException {
        ProgramEntity program = programRepository.getByProgramID(programID);
        if (program == null) {
            throw new NoProgramFoundException("NO SUCH PROGRAM WIH ID "+programID+".");
        }
        programRepository.delete(program);
        return modelMapper.map(program, ProgramDTO.class);
    }
}
