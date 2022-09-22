package com.practice_03.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import com.practice_02.ejb.SuperEJB;
import com.practice_03.model.Person;
import com.practice_03.remote.IPerson;

@Stateless
public class PersonImpl extends SuperEJB implements IPerson {
	
	@Override
	@Transactional
	public void doCreate(Person person) throws Exception {
		try {
			em.persist(person);
		} catch (Exception e) {
			throw new RuntimeException(e);

		}
	}

	public List<Person> findAll() throws Exception {
		return null;
	}

	public Person findById(String recipient) throws Exception {
		return null;
	}

	public List<Person> findAllByRecipient(String recipient) throws Exception {
		return null;
	}
}
