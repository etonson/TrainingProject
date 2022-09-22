package com.practice_03.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class SectionPK implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String departmentName;
	private String sectionName;


	public SectionPK() {
	}

	public SectionPK(String departmentName, String sectionName) {
		this.departmentName = departmentName;
		this.sectionName = sectionName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SectionPK other = (SectionPK) obj;
		if (departmentName == null) {
			if (other.departmentName != null)
				return false;
		} else if (!departmentName.equals(other.departmentName))
			return false;
		if (sectionName == null) {
			if (other.sectionName != null)
				return false;
		} else if (!sectionName.equals(other.sectionName))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
        int hash = 5;
        hash = 73 * hash + (this.departmentName != null ? this.departmentName.hashCode() : 0);
        hash = 73 * hash + (this.sectionName != null ? this.sectionName.hashCode() : 0);
        return hash;
	}
}
