package com.practice_03.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Eton {
	@Id
	private String id;
	private String nameX;
	private String number;
	private String sex;
}
