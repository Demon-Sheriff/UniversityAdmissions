package com.uniadmission.universityadmissions.controllers;

import java.util.*;

import com.uniadmission.universityadmissions.models.DTO.department.CreateDepartmentDTO;
import com.uniadmission.universityadmissions.models.DTO.department.DepartmentDTO;
import com.uniadmission.universityadmissions.models.DTO.department.UpdateDepartmentDTO;
import com.uniadmission.universityadmissions.views.UniversityDepartmentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v0/departments")
public class DepartmentController {

	private final UniversityDepartmentService departmentService;

	public DepartmentController(UniversityDepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@GetMapping("")
	public List<DepartmentDTO> getAllDepartments() {
		return departmentService.getAllDepartments();
	}

	@GetMapping("/{departmentID}")
	public DepartmentDTO getDepartmentByID(@PathVariable Long departmentID) {
		return departmentService.getDepartmentByID(departmentID);
	}

	@PostMapping("")
	public DepartmentDTO createDepartment(@RequestBody CreateDepartmentDTO createDepartmentDTO) {
		return departmentService.createDepartment(createDepartmentDTO);
	}

	@PutMapping("/{departmentID}")
	public DepartmentDTO updateDepartment(@PathVariable Long departmentID,
			@RequestBody UpdateDepartmentDTO updateDepartmentDTO) {
		return departmentService.updateDepartment(departmentID, updateDepartmentDTO);
	}

	// @PutMapping("/{departmentID}/updateStatus")
	// public DepartmentDTO updateDepartmentStatus(@PathVariable Long departmentID,
	// @RequestBody UpdateDepartmentStatusDTO updateDepartmentStatusDTO) {
	// return departmentService.updateDepartmentStatus(departmentID,
	// updateDepartmentStatusDTO);
	// }

	@DeleteMapping("/{departmentID}")
	public DepartmentDTO deleteDepartment(@PathVariable Long departmentID) {
		return departmentService.deleteDepartment(departmentID);
	}

}
