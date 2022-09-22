package com.practice_03.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import com.practice_02.ejb.SuperEJB;
import com.practice_03.model.Order;
import com.practice_03.remote.IOrder;

@Stateless
public class OrderImpl extends SuperEJB implements IOrder {

	@Override
	@Transactional
	public void doCreate(Order order) throws Exception {
		try {
			em.persist(order);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Order> findAll() throws Exception {
		return null;
	}

	public Order findById(String recipient) throws Exception {
		return null;
	}

	public List<Order> findAllByRecipient(String recipient) throws Exception {
		return null;
	}
}