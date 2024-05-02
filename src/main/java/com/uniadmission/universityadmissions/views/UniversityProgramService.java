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
        StringBuilder log = new StringBuilder("");
        log.append("ALL PROGRAMS FETCHED :-> ");
        if(department != -1) {
            log.append("ALL PROGRAMS FETCHED FOR DEPARTMENT ID "+department+" :-> ");
            DepartmentDTO departmentDTO = departmentService.getDepartmentByID(department);
            if (departmentDTO == null) {
                log.append("NO SUCH DEPARTMENT WITH ID "+department);
                AdmissionLogger.logError(log.toString());
                throw new NoProgramFoundException("NO SUCH DEPARTMENT WITH ID "+department+".");
            }
            DepartmentEntity departmentEntity = modelMapper.map(departmentDTO, DepartmentEntity.class);
            List<ProgramEntity> programs = programRepository.findByDepartment(departmentEntity);
            List<ProgramDTO> response = new ArrayList<>();
            for (ProgramEntity program : programs) {
                response.add(modelMapper.map(program, ProgramDTO.class));
            }
            return response;
        }

        List<ProgramEntity> programs = programRepository.findAll();
        List<ProgramDTO> response = new ArrayList<>();
        for (ProgramEntity program : programs) {
            response.add(modelMapper.map(program, ProgramDTO.class));
        }
        AdmissionLogger.logInfo(log.toString());
        return response;
    }

    public ProgramDTO getProgramById(Long id) throws NoProgramFoundException {
        ProgramEntity program = programRepository.findByProgramID(id);
        StringBuilder log = new StringBuilder("PROGRAM FETCHED :-> ");
        if (program == null) {
            log.append("NO SUCH PROGRAM WITH ID "+id);
            AdmissionLogger.logError(log.toString());
            throw new NoProgramFoundException("NO SUCH PROGRAM WIH ID "+id+".");
        }
        AdmissionLogger.logInfo(log.toString());
        return modelMapper.map(program, ProgramDTO.class);
    }

    public ProgramDTO createProgram(CreateProgramDTO createProgramDTO) throws BadRequestException {
        StringBuilder log = new StringBuilder("PROGRAM CREATED :-> ");
        if(createProgramDTO.getName() == null || createProgramDTO.getDepartment() == null) {
            log.append("BAD REQUEST :-> ");
            StringBuilder message = new StringBuilder("BAD REQUEST :-> ");
            if(createProgramDTO.getName() == null) {
                log.append(" PROGRAM NAME NOT FOUND ");
                message.append(" PROGRAM NAME NOT FOUND ");
            }
            if (createProgramDTO.getDepartment() == null) {
                log.append(" DEPARTMENT NOT FOUND ");
                message.append(" DEPARTMENT NOT FOUND ");
            }
            AdmissionLogger.logError(log.toString());
            throw new BadRequestException(message.toString());
        }
        ProgramEntity program = new ProgramEntity(createProgramDTO);
        program = programRepository.save(program);
        AdmissionLogger.logSuccess(log.toString());
        return modelMapper.map(program, ProgramDTO.class);
    }

    public ProgramDTO updateProgram(Long programID, UpdateProgramDTO updateProgramDTO) throws BadRequestException, NoProgramFoundException{
        StringBuilder log = new StringBuilder("PROGRAM UPDATED :-> ");
        if (programID == null) {
            log.append("BAD REQUEST :-> ");
            AdmissionLogger.logError(log.toString());
            throw new BadRequestException("BAD REQUEST :-> PROGRAM ID NOT FOUND.");
        }
        ProgramEntity program = programRepository.findByProgramID(programID);
        log.append("PROGRAM ID ").append(programID).append(" :-> ");
        if (program == null) {
            log.append("NO SUCH PROGRAM WITH ID ").append(programID);
            AdmissionLogger.logError(log.toString());
            throw new NoProgramFoundException("NO SUCH PROGRAM WIH ID "+programID+".");
        }
        if (updateProgramDTO.getName() != null) {
            log.append("NAME UPDATED TO ").append(updateProgramDTO.getName());
            program.setName(updateProgramDTO.getName());
        }
        if (updateProgramDTO.getDepartmentID() != null) {
            ProgramDTO programDTO = getProgramById(programID);
            if (programDTO == null) {
                log.append("NO SUCH PROGRAM WITH ID ").append(programID);
                AdmissionLogger.logError(log.toString());
                throw new NoProgramFoundException("NO SUCH PROGRAM WIH ID "+programID+".");
            }
            ProgramEntity programEntity = modelMapper.map(programDTO, ProgramEntity.class);
            program.setDepartment(programEntity.getDepartment());
            log.append("DEPARTMENT UPDATED TO ").append(updateProgramDTO.getDepartmentID());
        }
        if (updateProgramDTO.getDegreeLevel() != null) {
            log.append("DEGREE LEVEL UPDATED TO ").append(updateProgramDTO.getDegreeLevel());
            program.setName(updateProgramDTO.getName());
        }
        if (updateProgramDTO.getDuration() != null) {
            log.append("DURATION UPDATED TO ").append(updateProgramDTO.getDuration());
            program.setDuration(updateProgramDTO.getDuration());
        }
        program = programRepository.save(program);
        AdmissionLogger.logInfo(log.toString());
        return modelMapper.map(program, ProgramDTO.class);
    }

    public ProgramDTO deleteProgram(Long programID) throws NoProgramFoundException {
        ProgramEntity program = programRepository.findByProgramID(programID);
        StringBuilder log = new StringBuilder("PROGRAM DELETED :-> ");
        if (program == null) {
            log.append("NO SUCH PROGRAM WITH ID ").append(programID);
            AdmissionLogger.logError(log.toString());
            throw new NoProgramFoundException("NO SUCH PROGRAM WIH ID "+programID+".");
        }
        programRepository.delete(program);
        AdmissionLogger.logInfo(log.toString());
        return modelMapper.map(program, ProgramDTO.class);
    }
}
