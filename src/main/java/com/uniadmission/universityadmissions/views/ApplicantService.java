package com.uniadmission.universityadmissions.views;

import com.uniadmission.universityadmissions.exceptions.BadRequestException;
import com.uniadmission.universityadmissions.exceptions.applicantExceptions.NoApplicantFoundException;
import com.uniadmission.universityadmissions.models.DTO.applicant.ApplicantDTO;
import com.uniadmission.universityadmissions.models.DTO.applicant.CreateApplicantDTO;
import com.uniadmission.universityadmissions.models.DTO.applicant.UpdateApplicantAdmissionStatusDTO;
import com.uniadmission.universityadmissions.models.DTO.applicant.UpdateApplicantDTO;

import java.util.List;

public interface ApplicantService {

    List<ApplicantDTO> getAllApplicants();

    ApplicantDTO getApplicantById(Long id) throws NoApplicantFoundException;

    ApplicantDTO createApplicant(CreateApplicantDTO createApplicantDTO) throws BadRequestException;

    ApplicantDTO updateApplicant(Long applicantID, UpdateApplicantDTO updateApplicantDTO) throws BadRequestException;

    ApplicantDTO deleteApplicant(Long applicantID) throws BadRequestException, NoApplicantFoundException;

    ApplicantDTO updateApplicantStatus(Long applicantID, UpdateApplicantAdmissionStatusDTO updateApplicantStatusDTO) throws BadRequestException, NoApplicantFoundException;

}
