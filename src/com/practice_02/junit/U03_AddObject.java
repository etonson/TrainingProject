package com.practice_02.junit;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.After;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.practice_02.support.model.Area;
import com.practice_02.support.remote.AddObjectRemote;
import com.tool.EjbClient;

public class U03_AddObject {

	AddObjectRemote<Area> areaDAO;
	
	Area a = new Area();
	
	@BeforeEach
	protected void setUp() throws Exception {
		areaDAO = EjbClient.factory("Q03_AddObject");
		
		a.setID("areaID");
		a.setName("areaName");
	}
	
	@Test
	void testAddByEntityManager() throws Exception {
		Area data = areaDAO.addByEntityManager(a);
		
		Assert.assertEquals(data.getID(),"areaID");
		Assert.assertEquals(data.getName(),"AA");
	}

//	@Test
	void testAddWithNativeSQL() {
		fail("Not yet implemented");
	}

//	@Test
	void testAddWithNativeJPQL() {
		fail("Not yet implemented");
	}
	
	
	
	@After
	protected void reset() throws Exception {
		
	}
}
