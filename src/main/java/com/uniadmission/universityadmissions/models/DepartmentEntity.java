package com.uniadmission.universityadmissions.models;

import com.uniadmission.universityadmissions.models.DTO.department.CreateDepartmentDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "department_id", nullable = false)
	private long departmentID;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description", nullable = false)
	private String description;

	public DepartmentEntity(CreateDepartmentDTO createDepartmentDTO) {
		this.setName(createDepartmentDTO.getName());
		this.setDescription(createDepartmentDTO.getDescription());
	}

}
