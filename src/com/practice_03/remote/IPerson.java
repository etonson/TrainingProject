package com.practice_03.remote;

import java.util.List;

import com.practice_03.model.Person;
public interface IPerson {
	public void doCreate(Person person) throws Exception;
	
	public List<Person> findAll() throws Exception;

	public Person findById(String recipient) throws Exception;

	public List<Person> findAllByRecipient(String recipient) throws Exception;
}
