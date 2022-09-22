package com.practice_02.support.remote;

import javax.ejb.Remote;

@Remote
public interface UpdateObjectRemote<T> {

	T updateByEntityManager(T t) throws Exception;
	
	Integer updateWithNativeSQL(T t) throws Exception;
	
	Integer updateWithCriteria(T t) throws Exception;
	
	Integer updateWithJPQL(T t) throws Exception;
}
