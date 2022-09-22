package com.practice_03.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "TraingOrder")
@Data
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", columnDefinition = "nvarchar(270)")
	private String id;

	private int quantity;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@ManyToMany(mappedBy = "ownOrders")
//	@JoinTable(name = "Section_Order", 
//	inverseJoinColumns= {@JoinColumn(name = "SECTION_PK",referencedColumnName = "sectionPK")}
//	, joinColumns = {@JoinColumn(name = "ORDER_ID",referencedColumnName = "ID")})
	private List<Section> ownSections;

	@ManyToOne
	@JoinColumn(name = "WAREHOUSE_ID")
	private WareHouse toWareHouse;
	
	public Order() {}
	
	public Order(String id,int quantity,OrderStatus status) {
		this.id=id;
		this.quantity=quantity;
		this.status=status;
	}
	
	public enum OrderStatus {
		DEDICATED("DEDICATED", "預計投入"), 
		WIP("WIP", "work in process 已經投入"), 
		FG("FG", "完成");

		private String status;
		private String desc;

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		private OrderStatus(String status, String desc) {
			this.status = status;
			this.desc = desc;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public static OrderStatus getByName(String spName) throws Exception {

			for (OrderStatus orderType : OrderStatus.values()) {
				if (orderType.name().equals(spName.trim())) {
					return orderType;
				}
			}
			throw new Exception(String.format("There is no such kind of reason type named as the input:", spName));
		}

	}
}
