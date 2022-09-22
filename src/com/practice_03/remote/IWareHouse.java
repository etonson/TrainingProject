package com.practice_03.remote;

import java.util.List;

import com.practice_03.model.WareHouse;

public interface IWareHouse {
	public void doCreate(WareHouse wareHouse) throws Exception;
	
	public List<WareHouse> findAll() throws Exception;

	public WareHouse findById(String recipient) throws Exception;

	public List<WareHouse> findAllByRecipient(String recipient) throws Exception;
}
