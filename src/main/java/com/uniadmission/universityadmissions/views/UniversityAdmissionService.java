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

	public List<AdmissionDTO> getAllAdmissions() {
		String log = "FETCHED ALL ADMISSIONS :-> ";
		List<AdmissionEntity> admissions = admissionRepository.findAll();
		List<AdmissionDTO> response = new ArrayList<>();
		for (AdmissionEntity admission : admissions) {
			response.add(modelMapper.map(admission, AdmissionDTO.class));
		}
		AdmissionLogger.logInfo(log);
		return response;
	}

	public AdmissionDTO getAdmissionById(Long id) throws NoAdmissionFoundException {
		StringBuilder log = new StringBuilder("FETCHED ADMISSION :-> ").append(id);
		AdmissionEntity admission = admissionRepository.getByAdmissionID(id);
		if (admission == null) {
			log.append("NO SUCH ADMISSION WITH ID ").append(id);
			AdmissionLogger.logError(log.toString());
			throw new NoAdmissionFoundException("NO SUCH ADMISSION WITH ID " + id + ".");
		}
		AdmissionLogger.logInfo(log.toString());
		return modelMapper.map(admission, AdmissionDTO.class);
	}

	public AdmissionDTO createAdmission(CreateAdmissionDTO createAdmissionDTO)
			throws BadRequestException, NoProgramFoundException {

		StringBuilder log = new StringBuilder("ADMISSION CREATED :-> ");

		if (createAdmissionDTO.getApplicant() == null || createAdmissionDTO.getProgram() == null) {
			log.append("BAD REQUEST :-> ");
			StringBuilder message = new StringBuilder("BAD REQUEST :-> ");
			if (createAdmissionDTO.getApplicant() == null) {
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
		ApplicantDTO applicantDTO = applicantService.getApplicantById(createAdmissionDTO.getApplicant());
		if (applicantDTO == null) {
			log.append("NO SUCH APPLICANT WITH ID ").append(createAdmissionDTO.getApplicant());
			AdmissionLogger.logError(log.toString());
			throw new BadRequestException(" NO SUCH APPLICANT WITH ID " + createAdmissionDTO.getApplicant());
		}
		ProgramDTO programDTO = programService.getProgramById(createAdmissionDTO.getProgram());
		if (programDTO == null) {
			log.append(" NO SUCH PROGRAM WITH ID ").append(createAdmissionDTO.getProgram());
			AdmissionLogger.logError(log.toString());
			throw new BadRequestException(" NO SUCH PROGRAM WITH ID " + createAdmissionDTO.getProgram());

		}
		AdmissionEntity admission = new AdmissionEntity();
		admission.setApplicant(modelMapper.map(applicantDTO, ApplicantEntity.class));
		admission.setProgram(modelMapper.map(programDTO, ProgramEntity.class));

		AdmissionEntity admissionExists = admissionRepository.getAdmissionEntityByApplicantAndProgram(
				modelMapper.map(applicantDTO, ApplicantEntity.class), modelMapper.map(programDTO, ProgramEntity.class));
		if (admissionExists != null) {
			throw new BadRequestException("BAD REQUEST :-> ALREADY EXISTS");
		}
		log.append("CREATING ADMISSION FOR APPLICANT : ").append(createAdmissionDTO.getApplicant());
		admission = admissionRepository.save(admission);
		AdmissionLogger.logSuccess(log.toString());
		return modelMapper.map(admission, AdmissionDTO.class);
	}

	public AdmissionDTO updateAdmission(Long admissionID, UpdateAdmissionDTO updateAdmissionDTO)
			throws BadRequestException, NoProgramFoundException {
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
		log.append("ADMISSION ID : ").append(admissionID);
		AdmissionEntity currentAdmission = modelMapper.map(getAdmissionById(admissionID), AdmissionEntity.class);
		log.append("UPDATING PROGRAM : ").append(updateAdmissionDTO.getProgram());
		AdmissionDTO admissionDTO = getAdmissionById(admissionID);
		if (admissionDTO == null) {
			AdmissionLogger.logError(log.toString());
			throw new NoAdmissionFoundException("NO SUCH ADMISSION WITH ID " + admissionID);
		}
		if (updateAdmissionDTO.getProgram() != null) {
			ProgramDTO programDTO = programService.getProgramById(updateAdmissionDTO.getProgram());
			if (programDTO == null) {
				log.append("PROGRAM NOT FOUND ");
				AdmissionLogger.logError(log.toString());
				throw new BadRequestException("NO SUCH PROGRAM WITH ID " + updateAdmissionDTO.getProgram());
			}
			ProgramEntity program = modelMapper.map(programDTO, ProgramEntity.class);
			log.append("UPDATING PROGRAM : ").append(updateAdmissionDTO.getProgram());
			currentAdmission.setProgram(program);
		}
		else {
			log.append("NO PROGRAM TO UPDATE ");
		}
		currentAdmission = admissionRepository.save(currentAdmission);
		log.append("ADMISSION UPDATED ");
		return modelMapper.map(currentAdmission, AdmissionDTO.class);
	}

	public AdmissionDTO deleteAdmission(Long admissionID) throws BadRequestException, NoAdmissionFoundException {
		StringBuilder log = new StringBuilder("ADMISSION DELETED :-> ");
		if (admissionID == null) {
			log.append("BAD REQUEST :-> ADMISSION ID NULL");
			AdmissionLogger.logError(log.toString());
			throw new BadRequestException("BAD REQUEST :-> ADMISSION ID NOT FOUND");
		}
		AdmissionDTO admissionDTO = getAdmissionById(admissionID);
		if (admissionDTO == null) {
			log.append("NO SUCH ADMISSION WITH ID ").append(admissionID);
			AdmissionLogger.logError(log.toString());
			throw new NoAdmissionFoundException("NO SUCH ADMISSION WITH ID " + admissionID);
		}
		AdmissionEntity admission = modelMapper.map(admissionDTO, AdmissionEntity.class);
		admissionRepository.delete(admission);
		return modelMapper.map(admission, AdmissionDTO.class);
	}

	public AdmissionDTO updateAdmissionStatus(Long admissionID, UpdateAdmissionStatusDTO updateAdmissionStatusDTO)
			throws BadRequestException, NoAdmissionFoundException {
		StringBuilder log = new StringBuilder("ADMISSION STATUS UPDATED :-> ");
		if (admissionID == null) {
			log.append("BAD REQUEST :-> ADMISSION ID NULL");
			AdmissionLogger.logError(log.toString());
			throw new BadRequestException("BAD REQUEST :-> ADMISSION ID NOT FOUND");
		}
		if (updateAdmissionStatusDTO.getAdmissionStatus() == null) {
			log.append("BAD REQUEST :-> ADMISSION STATUS NOT FOUND");
			AdmissionLogger.logError(log.toString());
			throw new BadRequestException("BAD REQUEST :-> ADMISSION STATUS NOT FOUND");
		}
		AdmissionDTO admissionDTO = getAdmissionById(admissionID);
		if (admissionDTO == null) {
			log.append("NO SUCH ADMISSION WITH ID ").append(admissionID);
			AdmissionLogger.logError(log.toString());
			throw new NoAdmissionFoundException("NO SUCH ADMISSION WITH ID " + admissionID);
		}
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
