package com.practice_02.support.remote;

import javax.ejb.Remote;

@Remote
public interface AddObjectRemote<T>{

	T addByEntityManager(T t) throws Exception;
	
	T addWithNativeSQL(T t) throws Exception;
	
}
