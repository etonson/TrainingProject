package com.practice_02.ejb;


import javax.annotation.Resource;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

@TransactionManagement(TransactionManagementType.BEAN)
public abstract class SuperEJB {
	
    @PersistenceContext(unitName = "jpa-iPASP-StageDB")
	protected EntityManager em; 
    
    @Resource(lookup="PracticeDB")
	protected UserTransaction tx;
	
	
}
