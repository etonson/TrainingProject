package com.practice_02.support.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * 機台.
 * 
 * @author AAT
 *
 */
@Entity
public class Machine implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** (Key) 產線機台編號. */
	@EmbeddedId
	private MachinePK pk;

	/** 區域. */
	@ManyToOne
	@JoinColumn(name = "area_Id", referencedColumnName = "id")
	@JsonManagedReference
	private Area region;

	/** 廠內產線機台 ? ( Y/N ). */
	private boolean internalUsage;

	/** 產線機台名稱 ( 若名稱與備註都填入 Delete 表示刪除此產線機台資料 ). */
	private String name;

	/**
	 * 機台工時(可設定每日連續時間 不需要以每小時設定)
	 * 
	 */
	@OneToMany(mappedBy = "machine", fetch = FetchType.EAGER)
	@JsonBackReference
	private List<MachineWorkingTime> machineWorkingTimes;

	/** 額外資訊, 單向. */
	@OneToOne(cascade = CascadeType.ALL)
	private MachineExtraInfo machineextraInfo;

	/**
	 * Instantiates a new machine.
	 */
	public Machine() {

	}

	/**
	 * Instantiates a new machine.
	 */
	public Machine(MachinePK pk) {
		this.pk = pk;
	}

	public MachinePK getPk() {
		return pk;
	}

	public void setPk(MachinePK pk) {
		this.pk = pk;
	}

	public Area getRegion() {
		return region;
	}

	public void setRegion(Area region) {
		this.region = region;
	}

	public boolean isInternalUsage() {
		return internalUsage;
	}

	public void setInternalUsage(boolean internalUsage) {
		this.internalUsage = internalUsage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MachineWorkingTime> getMachineWorkingTimes() {
		return machineWorkingTimes;
	}

	public void setMachineWorkingTimes(List<MachineWorkingTime> machineWorkingTimes) {
		this.machineWorkingTimes = machineWorkingTimes;
	}

	public MachineExtraInfo getMachineextraInfo() {
		return machineextraInfo;
	}

	public void setMachineextraInfo(MachineExtraInfo machineextraInfo) {
		this.machineextraInfo = machineextraInfo;
	}

}
