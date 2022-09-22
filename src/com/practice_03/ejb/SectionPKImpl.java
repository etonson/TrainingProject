package com.practice_03.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import com.practice_02.ejb.SuperEJB;
import com.practice_03.model.SectionPK;
import com.practice_03.remote.ISectionPK;

@Stateless
public class SectionPKImpl extends SuperEJB implements ISectionPK {

	@Override
	@Transactional
	public void doCreate(SectionPK sectionPK) throws Exception {
		try {
			em.persist(sectionPK);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<SectionPK> findAll() throws Exception {
		return null;
	}

	public SectionPK findById(String recipient) throws Exception {
		return null;
	}

	public List<SectionPK> findAllByRecipient(String recipient) throws Exception {
		return null;
	}
}
