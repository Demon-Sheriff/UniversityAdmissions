package com.uniadmission.universityadmissions.views;

import com.uniadmission.universityadmissions.exceptions.BadRequestException;
import com.uniadmission.universityadmissions.exceptions.admissionExceptions.NoAdmissionFoundException;
import com.uniadmission.universityadmissions.exceptions.applicantExceptions.NoApplicantFoundException;
import com.uniadmission.universityadmissions.models.AdmissionEntity;
import com.uniadmission.universityadmissions.models.ApplicantEntity;
import com.uniadmission.universityadmissions.models.DTO.admission.AdmissionDTO;
import com.uniadmission.universityadmissions.models.DTO.admission.CreateAdmissionDTO;
import com.uniadmission.universityadmissions.models.DTO.admission.UpdateAdmissionDTO;
import com.uniadmission.universityadmissions.models.DTO.admission.UpdateAdmissionStatusDTO;
import com.uniadmission.universityadmissions.models.DTO.applicant.ApplicantDTO;
import com.uniadmission.universityadmissions.models.DTO.applicant.CreateApplicantDTO;
import com.uniadmission.universityadmissions.models.DTO.applicant.UpdateApplicantAdmissionStatusDTO;
import com.uniadmission.universityadmissions.models.DTO.applicant.UpdateApplicantDTO;
import com.uniadmission.universityadmissions.repositories.ApplicantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class ApplicantService {
    private final ApplicantRepository applicantRepository;
    private final ModelMapper modelMapper;
    public ApplicantService(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
        this.modelMapper = new ModelMapper();
    }

    public List<ApplicantDTO> getAllApplicants() {
        List<ApplicantEntity> applicants = applicantRepository.findAll();
        List<ApplicantDTO> response = new ArrayList<>();
        for(ApplicantEntity applicant : applicants) {
            response.add(modelMapper.map(applicant,ApplicantDTO.class));
        }
        return response;
    }

    public ApplicantDTO getApplicantById(Long id) throws NoApplicantFoundException {
        ApplicantEntity applicant = applicantRepository.getByApplicantID(id);
        if(applicant==null) {
            throw new NoApplicantFoundException("NO SUCH APPLICANT WITH ID"+id+".");
        }
        return modelMapper.map(applicant,ApplicantDTO.class);
    }


    public ApplicantDTO createApplicant(CreateApplicantDTO createApplicantDTO) throws BadRequestException {
        if(createApplicantDTO.getName()==null || createApplicantDTO.getDateOfBirth()==null || createApplicantDTO.getEmail()==null || createApplicantDTO.getAddress()==null) {
            StringBuilder message = new StringBuilder("BAD REQUEST :-> ");
            if(createApplicantDTO.getName()==null) {
                message.append("APPLICANT NAME NOT FOUND ");
            }
            if(createApplicantDTO.getDateOfBirth()==null) {
                message.append("APPLICANT DATE OF BIRTH NOT FOUND ");
            }
            if(createApplicantDTO.getEmail()==null) {
                message.append("APPLICANT EMAIL NOT FOUND ");
            }
            if(createApplicantDTO.getAddress()==null) {
                message.append("APPLICANT ADDRESS NOT FOUND");
            }
            throw new BadRequestException(message.toString());
        }
        ApplicantEntity applicant = new ApplicantEntity(createApplicantDTO);
        applicant = applicantRepository.save(applicant);
        return modelMapper.map(applicant,ApplicantDTO.class);
    }


    public ApplicantDTO updateApplicant(Long applicantId, UpdateApplicantDTO updateApplicantDTO) throws BadRequestException {
        if(applicantId==null || (updateApplicantDTO.getAddress()==null && updateApplicantDTO.getEmail()==null && updateApplicantDTO.getName()==null && updateApplicantDTO.getDateOfBirth()==null)) {
            StringBuilder message = new StringBuilder("BAD REQUEST :-> ");
            if(applicantId==null) {
                message.append("APPLICANT ID NOT FOUND ");
            }
            if(updateApplicantDTO.getAddress()==null && updateApplicantDTO.getEmail()==null && updateApplicantDTO.getName()==null && updateApplicantDTO.getDateOfBirth()==null) {
                message.append("INSUFFICIENT INFORMATION TO UPDATE THE APPLICANT DETAILS. AT LEAST ADD ONE FIELD TO UPDATE.");
            }
            throw new BadRequestException(message.toString());
        }
        ApplicantDTO applicant = new ApplicantDTO();
        ApplicantEntity currentApplicant = modelMapper.map(getApplicantById(applicantId),ApplicantEntity.class);
        if(updateApplicantDTO.getAddress()!=null) {
            currentApplicant.setAddress(updateApplicantDTO.getAddress());
        }
        if(updateApplicantDTO.getEmail()!=null) {
            currentApplicant.setEmail(updateApplicantDTO.getEmail());
        }
        if(updateApplicantDTO.getName()!=null) {
            currentApplicant.setName(updateApplicantDTO.getName());
        }
        if(updateApplicantDTO.getDateOfBirth()!=null) {
            currentApplicant.setName(updateApplicantDTO.getName());
        }
        currentApplicant = applicantRepository.save(currentApplicant);
        return modelMapper.map(currentApplicant,ApplicantDTO.class);
    }

    public ApplicantDTO deleteApplicant(Long applicantID) throws BadRequestException,NoApplicantFoundException {
        if(applicantID==null) throw new BadRequestException("BAD REQUEST :-> ADMISSION ID NOT FOUND");
        ApplicantDTO applicantDTO = getApplicantById(applicantID);
        if(applicantDTO==null) {
            throw new NoApplicantFoundException("NO SUCH APPLICANT WITH ID "+applicantID+".");
        }
        ApplicantEntity applicant = modelMapper.map(applicantDTO,ApplicantEntity.class);
        applicantRepository.delete(applicant);
        return modelMapper.map(applicant,ApplicantDTO.class);
    }

    public ApplicantDTO updateApplicantStatus(Long applicantID, UpdateApplicantAdmissionStatusDTO updateApplicantAdmissionStatusDTO) throws BadRequestException,NoApplicantFoundException {
        if (applicantID == null) throw new BadRequestException("BAD REQUEST :-> APPLICANT ID NOT FOUND ");
        if (updateApplicantAdmissionStatusDTO.getAppplicationStatus() == null) {
            throw new BadRequestException("BAD REQUEST :-> ADMISSION STATUS NOT FOUND");
        }
        ApplicantDTO applicantDTO = getApplicantById(applicantID);
        if (applicantDTO == null) throw new NoApplicantFoundException("NO SUCH APPLICANT WITH ID" + applicantID);
        ApplicantEntity applicant = modelMapper.map(applicantDTO, ApplicantEntity.class);
        applicant.setAppplicationStatus(updateApplicantAdmissionStatusDTO.getAppplicationStatus());
        return modelMapper.map(applicantRepository.save(applicant), ApplicantDTO.class);
    }














}
