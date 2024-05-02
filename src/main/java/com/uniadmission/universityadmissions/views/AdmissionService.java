package com.uniadmission.universityadmissions.views;

import com.uniadmission.universityadmissions.exceptions.BadRequestException;
import com.uniadmission.universityadmissions.exceptions.NoProgramFoundException;
import com.uniadmission.universityadmissions.exceptions.admissionExceptions.NoAdmissionFoundException;
import com.uniadmission.universityadmissions.models.DTO.admission.AdmissionDTO;
import com.uniadmission.universityadmissions.models.DTO.admission.CreateAdmissionDTO;
import com.uniadmission.universityadmissions.models.DTO.admission.UpdateAdmissionDTO;
import com.uniadmission.universityadmissions.models.DTO.admission.UpdateAdmissionStatusDTO;

import java.util.List;

public interface AdmissionService {

	List<AdmissionDTO> getAllAdmissions();

	AdmissionDTO getAdmissionById(Long id) throws NoAdmissionFoundException;

	AdmissionDTO createAdmission(CreateAdmissionDTO createAdmissionDTO)
			throws BadRequestException, NoProgramFoundException;

	AdmissionDTO updateAdmission(Long admissionID, UpdateAdmissionDTO updateAdmissionDTO)
			throws BadRequestException, NoProgramFoundException;

	AdmissionDTO deleteAdmission(Long admissionID) throws BadRequestException, NoAdmissionFoundException;

	AdmissionDTO updateAdmissionStatus(Long admissionID, UpdateAdmissionStatusDTO updateAdmissionStatusDTO)
			throws BadRequestException, NoAdmissionFoundException;

}
