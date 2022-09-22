package com.practice_02.support.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * 機台額外資訊.
 * @author AAT
 *
 */
@Entity
public class MachineExtraInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MachinePK machinepk; 
	
	//機台名稱別名
	private String machine_name_alias;
	
	public MachineExtraInfo() {
		super();
	}
	
	public MachineExtraInfo(MachinePK machinepk, String machine_name_alias) {
		super();
		this.machinepk = machinepk;
		this.machine_name_alias = machine_name_alias;
	}

	public MachinePK getMachinepk() {
		return machinepk;
	}

	public void setMachinepk(MachinePK machinepk) {
		this.machinepk = machinepk;
	}

	public String getMachine_name_alias() {
		return machine_name_alias;
	}

	public void setMachine_name_alias(String machine_name_alias) {
		this.machine_name_alias = machine_name_alias;
	}
	
	
}
