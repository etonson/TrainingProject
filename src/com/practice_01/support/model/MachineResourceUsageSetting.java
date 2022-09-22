package com.practice_01.support.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class MachineResourceUsageSetting  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private ResourceUsageSettingPK pk;
	@OneToMany(mappedBy = "machineResourceUsageSetting", cascade = { javax.persistence.CascadeType.ALL })
	@JsonBackReference
	private List<ProcessStage> stages;
	@ManyToOne(cascade = { javax.persistence.CascadeType.DETACH, javax.persistence.CascadeType.PERSIST })
	private Resource resource;
	@OneToOne
	private MachineX machine;

	public MachineResourceUsageSetting(ResourceUsageSettingPK pk) {
		this.pk = pk;
	}

	public MachineResourceUsageSetting() {
	}

	public MachineX getMachine() {
		return machine;
	}

	public void setMachine(MachineX machine) {
		this.machine = machine;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public List<ProcessStage> getStages() {
		return stages;
	}

	public void setStages(List<ProcessStage> stages) {
		this.stages = (stages == null ? null : new ArrayList(stages));
	}

	public ResourceUsageSettingPK getPk() {
		return pk;
	}

	public void setPk(ResourceUsageSettingPK pk) {
		this.pk = pk;
	}
}