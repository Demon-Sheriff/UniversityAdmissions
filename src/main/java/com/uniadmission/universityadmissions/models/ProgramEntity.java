package com.uniadmission.universityadmissions.models;

import com.uniadmission.universityadmissions.models.CustomStatus.DegreeLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgramEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long programID;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="department", nullable = false)
    private long departmentID;

    @Column(name="degreeLevel", nullable = false)
    private DegreeLevel degreeLevel;

    @Column(name="duration", nullable = false)
    private int duration;

}
