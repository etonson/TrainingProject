package com.practice_03.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import com.practice_02.ejb.SuperEJB;
import com.practice_03.model.Section;
import com.practice_03.remote.ISection;

@Stateless
public class SectionImpl extends SuperEJB implements ISection {

	@Override
	@Transactional
	public void doCreate(Section section) throws Exception {
		try {
			em.persist(section);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	@Transactional
	public void doMergeOrder(Section section) throws Exception {
		try {
			em.merge(section);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Section> findAll() throws Exception {
		return null;
	}

	public Section findById(String recipient) throws Exception {
		return null;
	}

	public List<Section> findAllByRecipient(String recipient) throws Exception {
		return null;
	}
}
