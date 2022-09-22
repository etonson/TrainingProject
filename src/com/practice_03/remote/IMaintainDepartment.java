package com.practice_03.remote;

import java.util.List;

import com.practice_03.model.Person;

public interface IMaintainDepartment {
	
	   public  List<Person> retriveManagersByDepartment(String departmentName) ; 
	   
	   public  int sumOrderQuantityByDepartment(String departmentName) ; 

}
