package com.practice_02.support.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

@Entity
public class MachineWorkingTime implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "machine_Id", referencedColumnName = "no"),
			@JoinColumn(name = "machine_Ver", referencedColumnName = "version") })
	private Machine machine;
	
	/**
	 * 一到日   1~7   
	 *  
	 */
	protected Integer dayOfWeek;
	/**
	 * 開始小時
	 */
	protected Integer startHour;
	/**
	 * 開始分鐘
	 */
	protected Integer startMinutes;
	/**
	 * 結束小時
	 */
	protected Integer endHour;
	/**
	 * 結束分鐘
	 */
	protected Integer endMinutes;
	
	
	
	
	/**
	 * Instantiates a new machine.
	 */
	public MachineWorkingTime(Integer id, Machine machine) {
		this.id = id;
		this.machine = machine;
	}
	
	/**
	 * Instantiates a new machine.
	 */
	public MachineWorkingTime(Integer id, Machine machine, Integer dayOfWeek) {
		this.id = id;
		this.machine = machine;
		this.dayOfWeek = dayOfWeek;
	}
	
	/**
	 * Instantiates a new machine.
	 */
	public MachineWorkingTime() {
		
	}
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Machine getMachine() {
		return machine;
	}
	public void setMachine(Machine machine) {
		this.machine = machine;
	}
	public Integer getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(Integer dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public Integer getStartHour() {
		return startHour;
	}
	public void setStartHour(Integer startHour) {
		this.startHour = startHour;
	}
	public Integer getStartMinutes() {
		return startMinutes;
	}
	public void setStartMinutes(Integer startMinutes) {
		this.startMinutes = startMinutes;
	}
	public Integer getEndHour() {
		return endHour;
	}
	public void setEndHour(Integer endHour) {
		this.endHour = endHour;
	}
	public Integer getEndMinutes() {
		return endMinutes;
	}
	public void setEndMinutes(Integer endMinutes) {
		this.endMinutes = endMinutes;
	}
}
