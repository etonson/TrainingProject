package com.practice_02.support.remote;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

@Remote
public interface FindObjectFromDatabaseRemote<T> {

	T findByEntityManager(String id) throws Exception;
	
	List<T> findByFilterWithNativeSQL(Map<String, Object> filter) throws Exception;
	
	List<T> findByFilterWithCriteria(Map<String, Object> filter) throws Exception;
	
	List<T> findByFilterJPQL(Map<String, Object> filter) throws Exception;
	
}
