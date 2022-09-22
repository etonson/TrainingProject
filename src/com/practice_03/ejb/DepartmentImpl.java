package com.practice_03.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.practice_03.model.Department;
import com.practice_03.remote.IDepartment;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DepartmentImpl implements IDepartment {
	@PersistenceContext(unitName = "jpa-iPASP-StageDB")
	protected EntityManager em;
	
	@Transactional
	public void doCreate(Department department) throws Exception {
		try {
			em.persist(department);
		} catch (Exception e) {
			throw new RuntimeException(e);

		}
	}
	
	public void doMergeOrder(Department department) throws Exception{}

	public List<Department> findAll() throws Exception {
		return null;
	}

	public Department findById(String recipient) throws Exception {
		return null;
	}

	public List<Department> findAllByRecipient(String recipient) throws Exception {
		return null;
	}
}
