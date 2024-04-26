package com.uniadmission.universityadmissions.views;

import com.uniadmission.universityadmissions.exceptions.BadRequestException;
import com.uniadmission.universityadmissions.exceptions.applicantExceptions.NoApplicantFoundException;
import com.uniadmission.universityadmissions.models.ApplicantEntity;
import com.uniadmission.universityadmissions.models.CustomStatus.AppplicationStatus;
import com.uniadmission.universityadmissions.models.DTO.applicant.ApplicantDTO;
import com.uniadmission.universityadmissions.models.DTO.applicant.CreateApplicantDTO;
import com.uniadmission.universityadmissions.models.DTO.applicant.UpdateApplicantAdmissionStatusDTO;
import com.uniadmission.universityadmissions.models.DTO.applicant.UpdateApplicantDTO;
import com.uniadmission.universityadmissions.repositories.ApplicantRepository;
import lombok.extern.flogger.Flogger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UniversityApplicantService implements ApplicantService{
    private final ApplicantRepository applicantRepository;
    private final ModelMapper modelMapper;
    public UniversityApplicantService(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
        this.modelMapper = new ModelMapper();
    }

    public List<ApplicantDTO> getAllApplicants() {
        StringBuilder log = new StringBuilder("ALL APPLICANTS FETCHED :-> ");
        List<ApplicantEntity> applicants = applicantRepository.findAll();
        List<ApplicantDTO> response = new ArrayList<>();
        for(ApplicantEntity applicant : applicants) {
            response.add(modelMapper.map(applicant,ApplicantDTO.class));
        }
        AdmissionLogger.logInfo(log.toString());
        return response;
    }

    public ApplicantDTO getApplicantById(Long id) throws NoApplicantFoundException {
        ApplicantEntity applicant = applicantRepository.getByApplicantID(id);
        StringBuilder log = new StringBuilder("APPLICANT FETCHED :-> ");
        if(applicant==null) {
            log.append("NO SUCH APPLICANT WITH ID "+id);
            AdmissionLogger.logError(log.toString());
            throw new NoApplicantFoundException("NO SUCH APPLICANT WITH ID "+id+".");
        }
        AdmissionLogger.logInfo(log.toString());
        return modelMapper.map(applicant,ApplicantDTO.class);
    }

    public AppplicationStatus getApplicantStatusById(Long id) {
        StringBuilder log = new StringBuilder("APPLICANT STATUS FETCHED :-> ");
        if (id == null) {
            log.append("BAD REQUEST :-> APPLICANT ID NOT FOUND ");
            throw new BadRequestException("BAD REQUEST :-> APPLICANT ID NOT FOUND");
        }
        ApplicantEntity applicant = modelMapper.map(getApplicantById(id), ApplicantEntity.class);
        if(applicant == null) {
            log.append("NO SUCH APPLICANT WITH ID " + id);
            throw new NoApplicantFoundException("NO SUCH APPLICANT WITH ID " + id);
        };
        AdmissionLogger.logInfo(log.toString());
        return applicant.getAppplicationStatus();
    }


    public ApplicantDTO createApplicant(CreateApplicantDTO createApplicantDTO) throws BadRequestException {
        StringBuilder log = new StringBuilder("APPLICANT CREATED :-> ");
        if(createApplicantDTO.getName()==null || createApplicantDTO.getDateOfBirth()==null || createApplicantDTO.getEmail()==null || createApplicantDTO.getAddress()==null) {
            log.append("BAD REQUEST :-> ");
            StringBuilder message = new StringBuilder("BAD REQUEST :-> ");
            if(createApplicantDTO.getName()==null) {
                log.append("APPLICANT NAME NOT FOUND ");
                message.append(" APPLICANT NAME NOT FOUND ");
            }
            if(createApplicantDTO.getDateOfBirth()==null) {
                log.append("APPLICANT DATE OF BIRTH NOT FOUND ");
                message.append(" APPLICANT DATE OF BIRTH NOT FOUND ");
            }
            if(createApplicantDTO.getEmail()==null) {
                log.append("APPLICANT EMAIL NOT FOUND ");
                message.append(" APPLICANT EMAIL NOT FOUND ");
            }
            if(createApplicantDTO.getAddress()==null) {
                log.append("APPLICANT ADDRESS NOT FOUND ");
                message.append(" APPLICANT ADDRESS NOT FOUND ");
            }

            AdmissionLogger.logError(log.toString());
            throw new BadRequestException(message.toString());
        }
        ApplicantEntity applicant = new ApplicantEntity(createApplicantDTO);
        log.append("APPLICANT CREATED ");
        applicant = applicantRepository.save(applicant);
        AdmissionLogger.logSuccess(log.toString());
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
            AdmissionLogger.logError(message.toString());
            throw new BadRequestException(message.toString());
        }
        ApplicantDTO applicant = new ApplicantDTO();
        ApplicantEntity currentApplicant = modelMapper.map(getApplicantById(applicantId),ApplicantEntity.class);
        StringBuilder log = new StringBuilder("APPLICANT DETAILS UPDATED :-> ");
        if(updateApplicantDTO.getAddress()!=null) {
            currentApplicant.setAddress(updateApplicantDTO.getAddress());
            log.append("ADDRESS UPDATED ");
        }
        if(updateApplicantDTO.getEmail()!=null) {
            currentApplicant.setEmail(updateApplicantDTO.getEmail());
            log.append("EMAIL UPDATED ");
        }
        if(updateApplicantDTO.getName()!=null) {
            currentApplicant.setName(updateApplicantDTO.getName());
            log.append("NAME UPDATED ");
        }
        if(updateApplicantDTO.getDateOfBirth()!=null) {
            currentApplicant.setName(updateApplicantDTO.getName());
            log.append("DATE OF BIRTH UPDATED ");
        }
        currentApplicant = applicantRepository.save(currentApplicant);
        AdmissionLogger.logSuccess(log.toString());
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
