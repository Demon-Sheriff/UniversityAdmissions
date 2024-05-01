package com.uniadmission.universityadmissions.views;

import com.uniadmission.universityadmissions.exceptions.BadRequestException;
import com.uniadmission.universityadmissions.exceptions.admissionExceptions.NoAdmissionFoundException;
import com.uniadmission.universityadmissions.models.AdmissionEntity;
import com.uniadmission.universityadmissions.models.DTO.admission.AdmissionDTO;
import com.uniadmission.universityadmissions.models.DTO.admission.CreateAdmissionDTO;
import com.uniadmission.universityadmissions.models.DTO.admission.UpdateAdmissionDTO;
import com.uniadmission.universityadmissions.models.DTO.admission.UpdateAdmissionStatusDTO;
import com.uniadmission.universityadmissions.repositories.AdmissionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class AdmissionService {

    private final AdmissionRepository admissionRepository;
    private final ModelMapper modelMapper;
    AdmissionService(AdmissionRepository admissionRepository) {
        this.admissionRepository = admissionRepository;
        this.modelMapper = new ModelMapper();
    }

    public List<AdmissionDTO> getAllAdmissions(){
        List<AdmissionEntity> admissions = admissionRepository.findAll();
        List<AdmissionDTO> response = new ArrayList<>();
        for (AdmissionEntity admission : admissions) {
            response.add(modelMapper.map(admission, AdmissionDTO.class));
        }
        return response;
    }

    public AdmissionDTO getAdmissionById(Long id) throws NoAdmissionFoundException {
        AdmissionEntity admission = admissionRepository.getByAdmissionID(id);
        if (admission == null) {
            throw new NoAdmissionFoundException("NO SUCH ADMISSION WIH ID "+id+".");
        }
        return modelMapper.map(admission, AdmissionDTO.class);
    }

    public AdmissionDTO createAdmission(CreateAdmissionDTO createAdmissionDTO) throws BadRequestException {
        if(createAdmissionDTO.getApplicant() == null || createAdmissionDTO.getProgram() == null) {
            StringBuilder message = new StringBuilder("BAD REQUEST :-> ");
            if(createAdmissionDTO.getApplicant() == null) message.append(" APPLICANT NOT FOUND ");
            if (createAdmissionDTO.getProgram() == null) message.append(" PROGRAM NOT FOUND ");
            throw new BadRequestException(message.toString());
        }
        AdmissionEntity admission = new AdmissionEntity(createAdmissionDTO);
        System.out.println(createAdmissionDTO.getApplicant());
        admission = admissionRepository.save(admission);
        return modelMapper.map(admission, AdmissionDTO.class);
    }

    public AdmissionDTO updateAdmission(Long admissionID, UpdateAdmissionDTO updateAdmissionDTO) throws BadRequestException{
        if (admissionID == null || updateAdmissionDTO.getProgram() == null) {
            StringBuilder message = new StringBuilder("BAD REQUEST :-> ");
            if (admissionID == null) message.append(" ADMISSION ID NOT FOUND ");
            if (updateAdmissionDTO.getProgram() == null) message.append(" PROGRAM ID NOT FOUND");
            throw new BadRequestException(message.toString());
        }
        AdmissionDTO admission = new AdmissionDTO();
        AdmissionEntity currentAdmission = modelMapper.map(getAdmissionById(admissionID), AdmissionEntity.class);
        if(updateAdmissionDTO.getProgram() != null) {
            currentAdmission.setProgram(updateAdmissionDTO.getProgram());
        }
        currentAdmission = admissionRepository.save(currentAdmission);
        return modelMapper.map(currentAdmission, AdmissionDTO.class);
    }

    public AdmissionDTO deleteAdmission(Long admissionID) throws BadRequestException, NoAdmissionFoundException {
        if(admissionID == null) throw new BadRequestException("BAD REQUEST :-> ADMISSION ID NOT FOUND");
        AdmissionDTO admissionDTO = getAdmissionById(admissionID);
        if (admissionDTO == null) {
            throw new NoAdmissionFoundException("NO SUCH ADMISSION WITH WITH ID "+admissionID);
        }
        AdmissionEntity admission = modelMapper.map(admissionDTO, AdmissionEntity.class);
        admissionRepository.delete(admission);
        return modelMapper.map(admission, AdmissionDTO.class);
    }

    public AdmissionDTO updateAdmissionStatus(Long admissionID, UpdateAdmissionStatusDTO updateAdmissionStatusDTO) throws BadRequestException, NoAdmissionFoundException {
        if (admissionID == null) throw new BadRequestException("BAD REQUEST :-> ADMISSION ID NOT FOUND");
        if(updateAdmissionStatusDTO.getAdmissionStatus() == null) throw new BadRequestException("BAD REQUEST :-> ADMISSION STATUS NOT FOUND");
        AdmissionDTO admissionDTO = getAdmissionById(admissionID);
        if (admissionDTO == null) throw new NoAdmissionFoundException("NO SUCH ADMISSION WITH ID "+admissionID);
        AdmissionEntity admission = modelMapper.map(admissionDTO, AdmissionEntity.class);
        admission.setAdmissionStatus(updateAdmissionStatusDTO.getAdmissionStatus());
        admission.setDecisionDate(new Date());
        return modelMapper.map(admissionRepository.save(admission), AdmissionDTO.class);
    }

}
