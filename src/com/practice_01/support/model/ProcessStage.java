package com.practice_01.support.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

@Entity
public class ProcessStage implements Serializable {
	@Id
	private String id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "resourceUsageSetting_Id", referencedColumnName = "no"),
			@javax.persistence.JoinColumn(name = "resourceUsageSetting_ver", referencedColumnName = "version") })
	private MachineResourceUsageSetting machineResourceUsageSetting;
	private static final long serialVersionUID = 1L;
	private int category;
	private String name;
	private int sequence;

	public ProcessStage() {
	}

	public ProcessStage(String id) {
		this.id = id;
	}

	public int getCategory() {
		return category;
	}

	public String getName() {
		return name;
	}

	public int getSequence() {
		return sequence;
	}

	public void setCategory(int newVal) {
		category = newVal;
	}

	public void setName(String newVal) {
		name = newVal;
	}

	public void setSequence(int newVal) {
		sequence = newVal;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MachineResourceUsageSetting getMachineResourceUsageSetting() {
		return machineResourceUsageSetting;
	}

	public void setMachineResourceUsageSetting(MachineResourceUsageSetting machineResourceUsageSetting) {
		this.machineResourceUsageSetting = machineResourceUsageSetting;
	}
}