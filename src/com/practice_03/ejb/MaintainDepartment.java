package com.practice_03.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.practice_02.ejb.SuperEJB;
import com.practice_03.model.Order;
import com.practice_03.model.Person;
import com.practice_03.remote.IMaintainDepartment;

@Stateless
public class MaintainDepartment extends SuperEJB implements IMaintainDepartment {

	@Override
	@Transactional
	public List<Person> retriveManagersByDepartment(String departmentName) {
		String indexNmae = departmentName;
		List<Person> persons = new ArrayList<Person>();
		try {
			Query query = em.createNativeQuery("SELECT * " + "	from Person as person"
					+ "		 LEFT JOIN Section as section on  section.MANAGER_EMPLOYEE_ID =person.EMPLOYEE_ID\r\n"
					+ "		 LEFT JOIN Department as department on department. MANAGER_EMPLOYEE_ID =  section.MANAGER_EMPLOYEE_ID \r\n"
					+ "			where department.DEPARTMENT_NAME = :departmentName ", Person.class);
			query.setParameter("departmentName", indexNmae);
			persons = query.getResultList();
		} catch (Exception ex) {
			Person person = new Person();
			person.setEmployeeId(0);
			person.setName("no data");
			persons.add(person);
			return persons;
		}

		return persons;
	}

	@Override
	@Transactional
	public int sumOrderQuantityByDepartment(String departmentName) {
		String indexNmae = departmentName;
		List<Order> orders = new ArrayList<Order>();
		int count = 0;
		try {
			Query totalQuantityQuery = em.createNativeQuery(
					  "select DISTINCT TraingOrder.quantity,"
					+ " TraingOrder.ID,TraingOrder.status,TraingOrder.WAREHOUSE_ID"
					+ " from Section_TraingOrder "
					+ " Left join TraingOrder on ownOrders_ID = TraingOrder.ID"
					+ " where ownOrders_ID in"
					+ " (SELECT ownOrders_ID FROM Section_TraingOrder)"
					+ " and ownSections_departmentName= :departmentName ",Order.class);
			totalQuantityQuery.setParameter("departmentName", indexNmae);
			orders = totalQuantityQuery.getResultList();
			for (int i = 0; i < orders.size(); i++) {
				count += orders.get(i).getQuantity();
			}
		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return count;
	}
}
