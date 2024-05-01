package com.uniadmission.universityadmissions.controllers;

import com.uniadmission.universityadmissions.exceptions.NoProgramFoundException;
import com.uniadmission.universityadmissions.models.DTO.program.CreateProgramDTO;
import com.uniadmission.universityadmissions.models.DTO.program.ProgramDTO;
import com.uniadmission.universityadmissions.models.ProgramEntity;
import com.uniadmission.universityadmissions.views.ProgramService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0/program")
public class ProgramController {
    private final ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }
    @GetMapping("")
    public List<ProgramDTO> getAllPrograms(){
        return programService.getAllPrograms();
    }

    @GetMapping("/{programID}")
    public ProgramDTO getProgramByID(@PathVariable Long programID) throws NoProgramFoundException {
        return programService.getProgramById(programID);
    }

    @PostMapping("")
    public ProgramDTO createProgram(@RequestBody CreateProgramDTO createProgramDTO) {
        return programService.createProgram(createProgramDTO);
    }

    @PostMapping("/{programID}")
    public ProgramDTO updateProgram(@PathVariable Long programID, @RequestBody CreateProgramDTO createProgramDTO) {
        return programService.updateProgram(programID, createProgramDTO);
    }

    @DeleteMapping("/{programID}")
    public ProgramDTO deleteProgram(@PathVariable Long programID) throws NoProgramFoundException {
        return programService.deleteProgram(programID);
    }
}
