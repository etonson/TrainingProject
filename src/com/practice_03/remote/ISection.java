package com.practice_03.remote;

import java.util.List;

import com.practice_03.model.Section;

public interface ISection {
	public void doCreate(Section section) throws Exception;
	
	public void doMergeOrder(Section section) throws Exception;
	
	public List<Section> findAll() throws Exception;

	public Section findById(String recipient) throws Exception;

	public List<Section> findAllByRecipient(String recipient) throws Exception;
}
