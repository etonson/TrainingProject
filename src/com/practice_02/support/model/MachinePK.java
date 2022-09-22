package com.practice_02.support.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MachinePK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Column(name = "no", nullable = false)
	private String id;

	/** The version. */
	private Integer version;

	/**
	 * Instantiates a new machine PK.
	 */
	public MachinePK() {

	}

	/**
	 * Instantiates a new machine PK.
	 *
	 * @param id      the id
	 * @param version the version
	 */
	public MachinePK(String id, Integer version) {
		super();
		this.id = id;
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MachinePK other = (MachinePK) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
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

}
