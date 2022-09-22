package com.practice_03.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Department  implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="DEPARTMENT_NAME")
	private String name;
	
    @OneToMany(mappedBy = "department")
	private List<Section> sections;

    @OneToOne
//    @JoinColumn(name="MANAGER_EMPLOYEE_ID")
	private Person manager;

	public Department() {
	}

	public Department(String name) {
		this.name = name;
	}
}
