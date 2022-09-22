package com.practice_03.remote;

import java.util.List;

import com.practice_03.model.Department;
public interface IDepartment {
	public void doCreate(Department department) throws Exception;
	
	public void doMergeOrder(Department department) throws Exception;
	
	public List<Department> findAll() throws Exception;

	public Department findById(String recipient) throws Exception;

	public List<Department> findAllByRecipient(String recipient) throws Exception;
}
