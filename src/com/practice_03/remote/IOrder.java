package com.practice_03.remote;

import java.util.List;

import com.practice_03.model.Order;

public interface IOrder {
	public void doCreate(Order order) throws Exception;
	
	public List<Order> findAll() throws Exception;

	public Order findById(String recipient) throws Exception;

	public List<Order> findAllByRecipient(String recipient) throws Exception;
}