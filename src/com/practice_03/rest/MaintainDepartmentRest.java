package com.practice_03.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.json.JSONArray;
import org.json.JSONObject;

import com.practice_03.model.Person;
import com.practice_03.remote.IMaintainDepartment;
import com.tool.JsoupWrapper;

@Path("httpTakeWork")
public class MaintainDepartmentRest {
	JsoupWrapper jsoup = new JsoupWrapper();
	@EJB
	IMaintainDepartment m1;

	@Path("ex01")
	@POST
	public String retriveManagerNamesByDepartment(String departmentName) {

		List<Person> persons = new ArrayList<Person>();
		persons = m1.retriveManagersByDepartment(departmentName);
		JSONArray jsonArr = null;
		JSONObject jsonObj = null;
		try {
			jsonArr = new JSONArray();
			for (int i = 0; i < persons.size(); i++) {
				jsonObj = new JSONObject();
				jsonObj.put("EmployeeId", persons.get(i).getEmployeeId());
				jsonObj.put("name", persons.get(i).getName());
				jsonArr.put(jsonObj);
			}
			System.out.println(jsonObj.toString());
			return jsonArr.toString();
		} catch (Exception je) {
			return null;
		}
	}
	
	@Path("ex02")
	@POST
	public String sumOrderQuantityByDepartment(String departmentName) {
		int count = m1.sumOrderQuantityByDepartment(departmentName);
		System.out.println("count--->"+count);
		return "result:"+String.valueOf(count);
	}

}
