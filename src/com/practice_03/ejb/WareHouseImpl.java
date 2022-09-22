package com.practice_03.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import com.practice_02.ejb.SuperEJB;
import com.practice_03.model.WareHouse;
import com.practice_03.remote.IWareHouse;

@Stateless
public class WareHouseImpl extends SuperEJB implements IWareHouse {

	@Override
	@Transactional
	public void doCreate(WareHouse wareHouse) throws Exception {
		try {
			em.persist(wareHouse);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<WareHouse> findAll() throws Exception {
		return null;
	}

	public WareHouse findById(String recipient) throws Exception {
		return null;
	}

	public List<WareHouse> findAllByRecipient(String recipient) throws Exception {
		return null;
	}
}
