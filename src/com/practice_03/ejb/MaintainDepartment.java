package com.practice_03.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.practice_02.ejb.SuperEJB;
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
//			ex.printStackTrace();
			return persons;
		}

		return persons;
	}

	@Override
	@Transactional
	public int sumOrderQuantityByDepartment(String departmentName) {
		String indexNmae = departmentName;
		try {
			Query totalQuantityQuery = em.createNativeQuery("SELECT SUM(quantity) from Section_TraingOrder "
					+ " Left join TraingOrder on ownOrders_ID = TraingOrder.ID"
					+ " where ownOrders_ID in (SELECT ownOrders_ID"
					+ " FROM Section_TraingOrder GROUP BY  ownOrders_ID  having count(*)>1)"
					+ "and ownSections_departmentName= :departmentName ");
			totalQuantityQuery.setParameter("departmentName", indexNmae);

			Query ReQuery = em.createNativeQuery("SELECT SUM(quantity) from Section_TraingOrder "
					+ " Left join TraingOrder on ownOrders_ID = TraingOrder.ID"
					+ " where ownOrders_ID in (SELECT ownOrders_ID"
					+ " FROM Section_TraingOrder GROUP BY  ownOrders_ID  having count(*)>1)"
					+ "and ownSections_departmentName= :departmentName ");
			ReQuery.setParameter("departmentName", indexNmae);

			int total = (int) totalQuantityQuery.getSingleResult();
			int total = (int) totalQuantityQuery.getSingleResult();
			int repeat = (int) ReQuery.getSingleResult();
			System.out.println("total" + total);
		} catch (Exception ex) {

			ex.printStackTrace();
		}
		int count = total - repeat;
		return count;
	}
}
