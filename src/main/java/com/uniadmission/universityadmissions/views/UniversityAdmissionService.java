package com.uniadmission.universityadmissions.views;

import com.uniadmission.universityadmissions.exceptions.BadRequestException;
import com.uniadmission.universityadmissions.exceptions.NoProgramFoundException;
import com.uniadmission.universityadmissions.exceptions.admissionExceptions.NoAdmissionFoundException;
import com.uniadmission.universityadmissions.models.AdmissionEntity;
import com.uniadmission.universityadmissions.models.ApplicantEntity;
import com.uniadmission.universityadmissions.models.CustomStatus.AdmissionStatus;
import com.uniadmission.universityadmissions.models.CustomStatus.AppplicationStatus;
import com.uniadmission.universityadmissions.models.DTO.admission.AdmissionDTO;
import com.uniadmission.universityadmissions.models.DTO.admission.CreateAdmissionDTO;
import com.uniadmission.universityadmissions.models.DTO.admission.UpdateAdmissionDTO;
import com.uniadmission.universityadmissions.models.DTO.admission.UpdateAdmissionStatusDTO;
import com.uniadmission.universityadmissions.models.DTO.applicant.ApplicantDTO;
import com.uniadmission.universityadmissions.models.DTO.program.ProgramDTO;
import com.uniadmission.universityadmissions.models.ProgramEntity;
import com.uniadmission.universityadmissions.repositories.AdmissionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UniversityAdmissionService implements AdmissionService {

    private final AdmissionRepository admissionRepository;

    private final ProgramService programService;

    private final ApplicantService applicantService;

    private final ModelMapper modelMapper;

    UniversityAdmissionService(AdmissionRepository admissionRepository, ProgramService programService,
        ApplicantService applicantService) {
      this.admissionRepository = admissionRepository;
      this.programService = programService;
      this.applicantService = applicantService;
      this.modelMapper = new ModelMapper();
    }
    public List<AdmissionDTO> getAllAdmissions(){
        StringBuilder log = new StringBuilder("ALL ADMISSIONS FETCHED :-> ");
        List<AdmissionEntity> admissions = admissionRepository.findAll();
        List<AdmissionDTO> response = new ArrayList<>();
        for (AdmissionEntity admission : admissions) {
            response.add(modelMapper.map(admission, AdmissionDTO.class));
        }
        AdmissionLogger.logInfo(log.toString());
        return response;
    }

    public AdmissionDTO getAdmissionById(Long id) throws NoAdmissionFoundException {
        StringBuilder log = new StringBuilder("ADMISSION FETCHED :-> ");
        AdmissionEntity admission = admissionRepository.getByAdmissionID(id);
        if (admission == null) {
            log.append("NO SUCH ADMISSION WITH ID "+id);
            throw new NoAdmissionFoundException("NO SUCH ADMISSION WITH ID "+id+".");
        }
        AdmissionLogger.logInfo(log.toString());
        return modelMapper.map(admission, AdmissionDTO.class);
    }

    public AdmissionDTO createAdmission(CreateAdmissionDTO createAdmissionDTO) throws BadRequestException {
        StringBuilder log = new StringBuilder("ADMISSION CREATED :-> ");
        if(createAdmissionDTO.getApplicant() == null || createAdmissionDTO.getProgram() == null) {
            log.append("BAD REQUEST :-> ");
            StringBuilder message = new StringBuilder("BAD REQUEST :-> ");
            if(createAdmissionDTO.getApplicant() == null) {
                log.append(" APPLICANT NOT FOUND ");
                message.append(" APPLICANT NOT FOUND ");
            }
            if (createAdmissionDTO.getProgram() == null) {
                log.append(" PROGRAM NOT FOUND ");
                message.append(" PROGRAM NOT FOUND ");
            }
            AdmissionLogger.logError(log.toString());
            throw new BadRequestException(message.toString());
        }

        AdmissionEntity admission = new AdmissionEntity(createAdmissionDTO);
        log.append("APPLICANT : "+createAdmissionDTO.getApplicant().getApplicantID());
        System.out.println(createAdmissionDTO.getApplicant());
        admission = admissionRepository.save(admission);
        AdmissionLogger.logSuccess(log.toString());
        return modelMapper.map(admission, AdmissionDTO.class);
    }

    public AdmissionDTO updateAdmission(Long admissionID, UpdateAdmissionDTO updateAdmissionDTO) throws BadRequestException{
        StringBuilder log = new StringBuilder("ADMISSION UPDATED :-> ");
        if (admissionID == null || updateAdmissionDTO.getProgram() == null) {
            log.append("BAD REQUEST :-> ");
            StringBuilder message = new StringBuilder("BAD REQUEST :-> ");
            if (admissionID == null) {
                log.append(" ADMISSIsON ID NOT FOUND ");
                message.append(" ADMISSION ID NOT FOUND ");
            }
            if (updateAdmissionDTO.getProgram() == null) {
                log.append(" PROGRAM ID NOT FOUND ");
                message.append(" PROGRAM ID NOT FOUND");
            }
            AdmissionLogger.logError(log.toString());
            throw new BadRequestException(message.toString());
        }
        AdmissionDTO admission = new AdmissionDTO();
        log.append("ADMISSION ID : "+admissionID);
        AdmissionEntity currentAdmission = modelMapper.map(getAdmissionById(admissionID), AdmissionEntity.class);
        log.append("PROGRAM : "+updateAdmissionDTO.getProgram().getProgramID());
        if(updateAdmissionDTO.getProgram() != null) {
            log.append("PROGRAM : "+updateAdmissionDTO.getProgramID());
            currentAdmission.setProgram(updateAdmissionDTO.getProgram());
        }else {
            log.append("PROGRAM NOT FOUND ");
        }
        currentAdmission = admissionRepository.save(currentAdmission);
        log.append("ADMISSION UPDATED ");
        return modelMapper.map(currentAdmission, AdmissionDTO.class);
    }

	public AdmissionDTO deleteAdmission(Long admissionID) throws BadRequestException, NoAdmissionFoundException {
		if (admissionID == null)
			throw new BadRequestException("BAD REQUEST :-> ADMISSION ID NOT FOUND");
		AdmissionDTO admissionDTO = getAdmissionById(admissionID);
		if (admissionDTO == null) {
			throw new NoAdmissionFoundException("NO SUCH ADMISSION WITH ID " + admissionID);
		}
		AdmissionEntity admission = modelMapper.map(admissionDTO, AdmissionEntity.class);
		admissionRepository.delete(admission);
		return modelMapper.map(admission, AdmissionDTO.class);
	}

	public AdmissionDTO updateAdmissionStatus(Long admissionID, UpdateAdmissionStatusDTO updateAdmissionStatusDTO)
			throws BadRequestException, NoAdmissionFoundException {
		if (admissionID == null)
			throw new BadRequestException("BAD REQUEST :-> ADMISSION ID NOT FOUND");
		if (updateAdmissionStatusDTO.getAdmissionStatus() == null)
			throw new BadRequestException("BAD REQUEST :-> ADMISSION STATUS NOT FOUND");
		AdmissionDTO admissionDTO = getAdmissionById(admissionID);
		if (admissionDTO == null)
			throw new NoAdmissionFoundException("NO SUCH ADMISSION WITH ID " + admissionID);
		AdmissionEntity admission = modelMapper.map(admissionDTO, AdmissionEntity.class);
		admission.setAdmissionStatus(updateAdmissionStatusDTO.getAdmissionStatus());
		admission.setDecisionDate(new Date());
		ApplicantEntity applicant = admission.getApplicant();
		if (updateAdmissionStatusDTO.getAdmissionStatus().equals(AdmissionStatus.ACCEPTED)) {
			applicant.setAppplicationStatus(AppplicationStatus.ACCEPTED);
		}
		if (updateAdmissionStatusDTO.getAdmissionStatus().equals(AdmissionStatus.PENDING)) {
			applicant.setAppplicationStatus(AppplicationStatus.UNDER_REVIEW);
		}
		if (updateAdmissionStatusDTO.getAdmissionStatus().equals(AdmissionStatus.WAITLISTED)) {
			applicant.setAppplicationStatus(AppplicationStatus.WAITLISTED);
		}
		return modelMapper.map(admissionRepository.save(admission), AdmissionDTO.class);
	}

}
