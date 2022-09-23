package com.practice_03.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Section implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Getter
	@Setter
	@EmbeddedId
	private SectionPK sectionPK;

	@Getter
	@Setter
	@ManyToMany
	private List<Order> ownOrders;

	@Getter
	@Setter
	@OneToOne
//	@JoinColumn(name = "MANAGER_EMPLOYEE_ID")
	private Person manager;
	

	@ManyToOne
	@JoinColumn(name = "departmentName", referencedColumnName = "DEPARTMENT_NAME",insertable = false,updatable = false)
	private Department department;
	
	public Section() {
	}

	public Section(SectionPK sectionPK, Person manager) {
		this.sectionPK=sectionPK;
		this.manager=manager;
	}

	public Section(SectionPK sectionPK, List<Order> ownOrders) {
		this.sectionPK=sectionPK;
		this.ownOrders=ownOrders;
	}
}
