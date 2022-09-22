package com.practice_03.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class WareHouse implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private String name;

    @OneToMany(mappedBy = "toWareHouse")
	private List<Order> orders;
	
	//初始化---
	
	public WareHouse() {
	}
	public WareHouse(String name) {
		this.name = name;
	}
	public WareHouse(String name, List<Order> orders) {
		this.name = name;
		this.orders = orders;
	}
}