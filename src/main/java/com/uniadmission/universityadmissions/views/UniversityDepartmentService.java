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
        StringBuilder log = new StringBuilder("ALL DEPARTMENTS FETCHED :-> ");
        List<DepartmentEntity> departments = departmentRepository.findAll();
        List<DepartmentDTO> response = new ArrayList<>();
        for (DepartmentEntity department : departments) {
            response.add(modelMapper.map(department, DepartmentDTO.class));
        }
        AdmissionLogger.logInfo(log.toString());
        return response;
    }

    public DepartmentDTO getDepartmentByID(Long departmentID) throws NoDepartmentFoundException {
        StringBuilder log = new StringBuilder("DEPARTMENT FETCHED :-> ");
        DepartmentEntity department = departmentRepository.findByDepartmentID(departmentID);
        if(department == null) {
            log.append("NO SUCH DEPARTMENT WITH ID "+departmentID);
            throw new NoDepartmentFoundException("NO SUCH DEPARTMENT WITH ID "+departmentID+".");
        }
        AdmissionLogger.logInfo(log.toString());
        return modelMapper.map(department, DepartmentDTO.class);
    }

    public DepartmentDTO createDepartment(CreateDepartmentDTO createDepartmentDTO) throws BadRequestException {
        StringBuilder log = new StringBuilder("DEPARTMENT CREATED :-> ");
        if(createDepartmentDTO.getName() == null || createDepartmentDTO.getDescription() == null) {
            log.append("BAD REQUEST :-> ");
            StringBuilder message = new StringBuilder("BAD REQUEST :-> ");
            if(createDepartmentDTO.getName() == null) {
                log.append(" DEPARTMENT NAME NOT FOUND ");
                message.append(" DEPARTMENT NAME NOT FOUND ");
            };
            if(createDepartmentDTO.getDescription() == null) {
                log.append(" DEPARTMENT DESCRIPTION NOT FOUND ");
                message.append(" DEPARTMENT DESCRIPTION NOT FOUND ");
            };
            throw new BadRequestException(message.toString());
        }
        DepartmentEntity department = new DepartmentEntity(createDepartmentDTO);
        department = departmentRepository.save(department);
        AdmissionLogger.logInfo(log.toString());
        return modelMapper.map(department, DepartmentDTO.class);
    }

    public DepartmentDTO updateDepartment(Long departmentID, UpdateDepartmentDTO updateDepartmentDTO) throws BadRequestException {
        // DepartmentDTO department = new DepartmentDTO();
        DepartmentEntity currentDepartment = modelMapper.map(getDepartmentByID(departmentID), DepartmentEntity.class);
        StringBuilder log = new StringBuilder("DEPARTMENT UPDATED :-> ");
        if(updateDepartmentDTO.getName() != null) {
            log.append("NAME UPDATED TO "+updateDepartmentDTO.getName());
            currentDepartment.setName(updateDepartmentDTO.getName());
        }
        else{
            log.append("NAME NOT UPDATED ");
        }
        if(updateDepartmentDTO.getDescription() != null) {
            log.append("DESCRIPTION UPDATED TO "+updateDepartmentDTO.getDescription());
            currentDepartment.setDescription(updateDepartmentDTO.getDescription());
        }
        else {
            log.append("DESCRIPTION NOT UPDATED ");
        }
        currentDepartment = departmentRepository.save(currentDepartment);
        AdmissionLogger.logInfo(log.toString());
        return modelMapper.map(currentDepartment, DepartmentDTO.class);
    }

    public DepartmentDTO deleteDepartment(Long departmentID) throws BadRequestException, NoDepartmentFoundException {
        StringBuilder log = new StringBuilder("");
        if(departmentID == null) {
            log.append("BAD REQUEST :-> DEPARTMENT ID NOT FOUND ");
            throw new BadRequestException("BAD REQUEST :-> DEPARTMENT ID NOT FOUND");
        };
        DepartmentDTO departmentDTO = getDepartmentByID(departmentID);
        log.append("DEPARTMENT DELETED :-> "+departmentDTO.getName());
        if (departmentDTO == null) {
            log.append("NO SUCH DEPARTMENT WITH WITH GIVEN ID "+departmentID);
            throw new NoDepartmentFoundException("NO SUCH DEPARTMENT WITH WITH GIVEN ID "+departmentID);
        }
        // map the DTO to the entity class
        DepartmentEntity department = modelMapper.map(departmentDTO, DepartmentEntity.class);
        departmentRepository.delete(department);
        AdmissionLogger.logInfo(log.toString());
        return modelMapper.map(department, DepartmentDTO.class);
    }

}
