package com.practice_01.support.ejb;

import javax.ejb.Remote;

@Remote
public abstract interface IBasicDataPreparation {
	public abstract void prepareBasicData();

	public abstract void cleanData();
	
	public abstract void practice_02_initinalData();
	
	public abstract void practice_02_resetData();

	public abstract void practice_02_initinalDataMachine();
	
	public abstract void practice_02_resetDataMachine();
}