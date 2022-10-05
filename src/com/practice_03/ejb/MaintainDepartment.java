package com.practice_03.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import com.practice_02.ejb.SuperEJB;
import com.practice_03.model.Order;
import com.practice_03.model.Person;
import com.practice_03.model.Section;
import com.practice_03.remote.IMaintainDepartment;

@Stateless
public class MaintainDepartment extends SuperEJB implements IMaintainDepartment {

	@Override
	@Transactional
	public List<Person> retriveManagersByDepartment(String departmentName) {
		List<Person> persons = new ArrayList<Person>();
		try {
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<Person> criteriaQuery = criteriaBuilder.createQuery(null);
			Root<Section> rootSection = criteriaQuery.from(Section.class);
			
			Join<Section,Person> joinPerson = rootSection.join("manager", JoinType.LEFT);
			joinPerson.on(criteriaBuilder.equal(rootSection.get("manager").get("employeeId"),joinPerson.get("employeeId")));

			Predicate p1 = criteriaBuilder.equal(rootSection.get("department").get("name"), departmentName);
			criteriaQuery.where(p1);
			criteriaQuery.select(joinPerson);
			TypedQuery<Person> typedQuery = em.createQuery(criteriaQuery);
			persons = typedQuery.getResultList();
			return persons;

		} catch (Exception ex) {
			ex.getCause();
			ex.printStackTrace();
			return null;
		}
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
