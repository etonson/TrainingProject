package com.practice_03.remote;

import java.util.List;

import com.practice_03.model.SectionPK;

public interface ISectionPK {
	public void doCreate(SectionPK sectionPK) throws Exception;
	
	public List<SectionPK> findAll() throws Exception;

	public SectionPK findById(String recipient) throws Exception;

	public List<SectionPK> findAllByRecipient(String recipient) throws Exception;
}
