package com.uniadmission.universityadmissions.controllers;

import com.uniadmission.universityadmissions.exceptions.NoProgramFoundException;
import com.uniadmission.universityadmissions.models.DTO.program.CreateProgramDTO;
import com.uniadmission.universityadmissions.models.DTO.program.ProgramDTO;
import com.uniadmission.universityadmissions.models.DTO.program.UpdateProgramDTO;
import com.uniadmission.universityadmissions.views.UniversityProgramService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0/program")
public class ProgramController {
    private final UniversityProgramService programService;

    public ProgramController(UniversityProgramService programService) {
        this.programService = programService;
    }
    @GetMapping("")
    public List<ProgramDTO> getAllPrograms(
            @RequestParam(name = "department", defaultValue = "-1") Long departmentID
    ){
        Long department = -1L;
        if(departmentID != null) {
            department = departmentID;
        }
        return programService.getAllPrograms(department);
    }

    @GetMapping("/{programID}")
    public ProgramDTO getProgramByID(@PathVariable Long programID) throws NoProgramFoundException {
        return programService.getProgramById(programID);
    }


    @PostMapping("")
    public ProgramDTO createProgram(@RequestBody CreateProgramDTO createProgramDTO) {
        return programService.createProgram(createProgramDTO);
    }

    @PutMapping("/{programID}")
    public ProgramDTO updateProgram(@PathVariable Long programID, @RequestBody UpdateProgramDTO updateProgramDTO) throws NoProgramFoundException {
        return programService.updateProgram(programID, updateProgramDTO);
    }

    @DeleteMapping("/{programID}")
    public ProgramDTO deleteProgram(@PathVariable Long programID) throws NoProgramFoundException {
        return programService.deleteProgram(programID);
    }
}
