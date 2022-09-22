package com.practice_01.support.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ResourceUsageSettingPK implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "no", nullable = false)
	private String id;
	private Integer version;

	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o == this) {
			return true;
		}
		if (!(o instanceof ResourceUsageSettingPK)) {
			return false;
		}
		ResourceUsageSettingPK pk = (ResourceUsageSettingPK) o;
		if ((id == null) || (!id.equals(pk.getId()))) {
			return false;
		}
		if ((version == null) || (pk.getVersion() == null) || (version != pk.getVersion())) {
			return false;
		}
		return true;
	}

	public int hashCode() {
		return id.hashCode() + version.intValue();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public ResourceUsageSettingPK(String id, Integer version) {
		this.id = id;
		this.version = version;
	}

	public ResourceUsageSettingPK() {
	}
}