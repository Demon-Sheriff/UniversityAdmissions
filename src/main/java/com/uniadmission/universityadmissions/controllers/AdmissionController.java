package com.uniadmission.universityadmissions.controllers;

import com.uniadmission.universityadmissions.models.DTO.admission.AdmissionDTO;
import com.uniadmission.universityadmissions.models.DTO.admission.CreateAdmissionDTO;
import com.uniadmission.universityadmissions.models.DTO.admission.UpdateAdmissionDTO;
import com.uniadmission.universityadmissions.models.DTO.admission.UpdateAdmissionStatusDTO;
import com.uniadmission.universityadmissions.views.UniversityAdmissionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v0/admissions")
public class AdmissionController {

    private final UniversityAdmissionService admissionService;

    public AdmissionController(UniversityAdmissionService admissionService) {
        this.admissionService = admissionService;
    }

    @GetMapping("")
    public List<AdmissionDTO> getAllAdmissions(){
        return admissionService.getAllAdmissions();
    }

    @GetMapping("/{admissionID}")
    public AdmissionDTO getAdmissionByID(@PathVariable Long admissionID) {
        return admissionService.getAdmissionById(admissionID);
    }

    @PostMapping("")
    public AdmissionDTO createAdmission(@RequestBody  CreateAdmissionDTO createAdmissionDTO) {
        return admissionService.createAdmission(createAdmissionDTO);
    }

    @PutMapping("/{admissionID}")
    public AdmissionDTO updateAdmission(@PathVariable Long admissionID, @RequestBody UpdateAdmissionDTO updateAdmissionDTO) {
        return admissionService.updateAdmission(admissionID, updateAdmissionDTO);
    }

    @PutMapping("/{admissionID}/updateStatus")
    public AdmissionDTO updateAdmissionStatus(@PathVariable Long admissionID, @RequestBody UpdateAdmissionStatusDTO updateAdmissionStatusDTO) {
        return admissionService.updateAdmissionStatus(admissionID, updateAdmissionStatusDTO);
    }

    @DeleteMapping("/{admissionID}")
    public AdmissionDTO deleteAdmission(@PathVariable Long admissionID) {
        return admissionService.deleteAdmission(admissionID);
    }





}
