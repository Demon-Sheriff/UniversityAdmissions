package com.uniadmission.universityadmissions.controllers;

import com.uniadmission.universityadmissions.models.CustomStatus.AppplicationStatus;
import com.uniadmission.universityadmissions.models.DTO.applicant.ApplicantDTO;
import com.uniadmission.universityadmissions.models.DTO.applicant.CreateApplicantDTO;
import com.uniadmission.universityadmissions.models.DTO.applicant.UpdateApplicantAdmissionStatusDTO;
import com.uniadmission.universityadmissions.models.DTO.applicant.UpdateApplicantDTO;
import com.uniadmission.universityadmissions.views.UniversityApplicantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0/applicants")
public class ApplicantController {
    private final UniversityApplicantService applicantService;

    public ApplicantController(UniversityApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @GetMapping("")
    public List<ApplicantDTO> getAllApplicants() {
        return applicantService.getAllApplicants();
    }

    @GetMapping("/{applicantID}")
    public ApplicantDTO getApplicantByID(@PathVariable Long applicantID) {
        return applicantService.getApplicantById(applicantID);
    }

    @GetMapping("/{applicantID}/status")
    public AppplicationStatus getApplicantStatusById(@PathVariable Long applicantID) {
        return applicantService.getApplicantStatusById(applicantID);
    }

    @PostMapping("")
    public ApplicantDTO createApplicant(@RequestBody CreateApplicantDTO createApplicantDTO) {
        System.out.println(createApplicantDTO.getDateOfBirth().getClass());
        return applicantService.createApplicant(createApplicantDTO);
    }

    @PutMapping("/{applicantID}")
    public ApplicantDTO updateApplicant(@PathVariable Long applicantID, @RequestBody UpdateApplicantDTO updateApplicantDTO) {
        return applicantService.updateApplicant(applicantID,updateApplicantDTO);
    }

    @PutMapping("/{applicantID}/updateStatus")
    public ApplicantDTO updateApplicantStatus(@PathVariable Long applicantID, @RequestBody UpdateApplicantAdmissionStatusDTO updateApplicantAdmissionStatusDTO) {
        return applicantService.updateApplicantStatus(applicantID,updateApplicantAdmissionStatusDTO);
    }

    @DeleteMapping("/{applicantID}")
    public ApplicantDTO deleteApplicant(@PathVariable Long applicantID) {
        return applicantService.deleteApplicant(applicantID);
    }

}
