package com.practice_02.support.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * 區域
 * @author AAT
 *
 */
@Entity
public class Area implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 區域編號
	 */
	@Id
	@Column(columnDefinition = "nvarchar(270)")
	private String ID;
	
	public enum AreaType {

		/** The aps. */
		APS("APS", "排程使用的區"),

		/** The wms. */
		WMS("WMS", "Warehouse Management System使用的區"),

		/** The physical. */
		PHYSICAL("PHYSICAL", "實際放置的位子");

		/** The status. */
		private String status;

		/** The desc. */
		private String desc;

		/**
		 * Gets the desc.
		 *
		 * @return the desc
		 */
		public String getDesc() {
			return desc;
		}

		/**
		 * Sets the desc.
		 *
		 * @param desc the new desc
		 */
		public void setDesc(String desc) {
			this.desc = desc;
		}

		/**
		 * Instantiates a new area type.
		 *
		 * @param status the status
		 * @param desc   the desc
		 */
		private AreaType(String status, String desc) {
			this.status = status;
			this.desc = desc;
		}

		/**
		 * Gets the status.
		 *
		 * @return the status
		 */
		public String getStatus() {
			return status;
		}

		/**
		 * Sets the status.
		 *
		 * @param status the new status
		 */
		public void setStatus(String status) {
			this.status = status;
		}

		
		
		public static AreaType getByName(String spName) throws Exception {

			for (AreaType areaType : AreaType.values()) {
				if (areaType.name().equals(spName.trim())) {
					return areaType;
				}
			}
			throw new Exception(String.format("There is no such kind of reason type named as the input:", spName));
		}
		
		
	}
	
	/** 區域名稱. */
	private String name;
	
	/**
	 * 區域類別 APS:實際排程用的區 WMS:Warehouse Management System資料使用的區，只做前端頁面顯示用，如:產品/物料的儲位.
	 */
	@Enumerated(EnumType.STRING)
	private AreaType category = AreaType.APS;

	
	/**
	 * Instantiates a new area.
	 */
	public Area(String ID, String name, AreaType type) {
		this.ID = ID;
		this.name = name;
		this.category = type;
	}
	
	/**
	 * Instantiates a new area.
	 */
	public Area(String ID) {
		this.ID = ID;
	}
	
	/**
	 * Instantiates a new area.
	 */
	public Area() {

	}
	
	

	/** Machine 機台. */
	@OneToMany(mappedBy = "region")
	@JsonBackReference
	private List<Machine> machines;
	
	
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AreaType getCategory() {
		return category;
	}

	public void setCategory(AreaType category) {
		this.category = category;
	}

	public List<Machine> getMachines() {
		return machines;
	}

	public void setMachines(List<Machine> machines) {
		this.machines = machines;
	}
	
}
