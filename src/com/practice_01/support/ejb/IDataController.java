package com.practice_01.support.ejb;

import java.util.Map;

import javax.ejb.Remote;

import com.practice_01.support.model.MachineResourceUsageSetting;
import com.practice_01.support.model.MachineX;
import com.practice_01.support.model.ProcessStage;
import com.practice_01.support.model.Resource;

@Remote
public interface IDataController {
	
	void addControl(MachineResourceUsageSetting t) throws Exception;
	
	MachineResourceUsageSetting validateAddControl() throws Exception;
	
	void updateControl(Resource t) throws Exception;
	
	Resource validateUpdateControl() throws Exception;
	
	ProcessStage retriveControl(Map<String, Object> filter) throws Exception;
	
	MachineX retriveControl(String machinexname) throws Exception;
	
	void deleteControl(MachineX t) throws Exception;
	
	MachineX validateDeleteControl() throws Exception;


}
