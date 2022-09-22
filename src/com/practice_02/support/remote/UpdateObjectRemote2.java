package com.practice_02.support.remote;

import javax.ejb.Remote;

@Remote
public interface UpdateObjectRemote2<T> {

	T updateByEntityManager(T t) throws Exception;
	
	T updateWithNativeSQL(T t) throws Exception;
	
	T updateWithCriteria(T t) throws Exception;
	
	T updateWithJPQL(T t) throws Exception;
}
