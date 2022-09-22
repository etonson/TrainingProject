package com.practice_02.support.remote;

import javax.ejb.Remote;

@Remote
public interface DeleteObjectRemote<T> {

	Integer deleteByEntityManager(T t) throws Exception;
	
	Integer deleteWithNativeSQL(T t) throws Exception;
	
	Integer deleteWithCriteria(T t) throws Exception;
	
	Integer deleteWithJPQL(T t) throws Exception;
}
