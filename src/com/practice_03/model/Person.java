package com.practice_03.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "EMPLOYEE_ID", columnDefinition = "nvarchar(30)")
	private int employeeId;
	
	@Column(columnDefinition = "nvarchar(50)")
	private String name;

	public Person() {
	}

	public Person(int employeeId) {
		this.employeeId = employeeId;
	}
	public Person(int employeeId,String name) {
		this.employeeId = employeeId;
		this.name = name;
	}
}
