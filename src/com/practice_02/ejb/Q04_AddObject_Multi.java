package com.practice_02.ejb;






import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import com.practice_02.support.model.Area;
import com.practice_02.support.model.Machine;
import com.practice_02.support.model.MachineExtraInfo;
import com.practice_02.support.model.MachinePK;
import com.practice_02.support.model.MachineWorkingTime;
import com.practice_02.support.remote.AddObjectRemote;



@Stateless	
public class Q04_AddObject_Multi extends SuperEJB implements AddObjectRemote<Machine> {

	private final Log log = LogFactory.getLog(Q04_AddObject_Multi.class);
	
	/**
	 * 4-1
	 */
	@Override
	public Machine addByEntityManager(Machine t) throws Exception {

		try {
			tx.begin();
//			**************************************
			// TODO Auto-generated method stub
			MachinePK machinePK = new MachinePK("machineA",20001);
			t.setPk(machinePK);
			t.setName("nameA");

			Area area = new Area("zoon100");
			area.setName("ABC");
			t.setRegion(area);
			em.persist(area);
			
			MachineExtraInfo machineExtraInfo =new MachineExtraInfo(machinePK,"aliasNameAA");
			t.setMachineextraInfo(machineExtraInfo);
			em.persist(machineExtraInfo);//check ok
			
			List<MachineWorkingTime> listMachineWorkingTime = new ArrayList<MachineWorkingTime>();
			MachineWorkingTime machineWorkingTime =null;
			for(int i=1;i<8;i++) 
			{
				machineWorkingTime = new MachineWorkingTime();
				machineWorkingTime.setId(i+100);
				machineWorkingTime.setDayOfWeek(i);
				machineWorkingTime.setStartHour(9);
				machineWorkingTime.setStartMinutes(0);
				machineWorkingTime.setEndHour(18);
				machineWorkingTime.setEndMinutes(0);
				machineWorkingTime.setMachine(t);
				listMachineWorkingTime.add(machineWorkingTime);
			}
			t.setMachineWorkingTimes(listMachineWorkingTime);
			em.persist(t);
			for(int i=0 ;i<listMachineWorkingTime.size();i++) 
			{
				em.persist(listMachineWorkingTime.get(i));
			}
//			**************************************
			tx.commit();
			
			return findById(t.getPk());
		}catch(Exception e) {
			tx.rollback();
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 4-2
	 */
	@Override
	public Machine addWithNativeSQL(Machine t) throws Exception {
		try {
			tx.begin();
//			**************************************
			// TODO Auto-generated method stub
			Query query01 = em.createNativeQuery(
					"INSERT INTO MachineExtraInfo"
					+ " (MachineExtraInfo.no"
					+ " ,MachineExtraInfo.version"
					+ " ,MachineExtraInfo.machine_name_alias)"
					+ " VALUES"
					+ " ('machineB',30001,'aliasNameBB')",
					MachineExtraInfo.class);
			query01.executeUpdate();
			Query query02 = em.createNativeQuery("INSERT INTO Machine(no ,version ,internalUsage ,name,machineextraInfo_no"
					+ " ,machineextraInfo_version ,area_Id) VALUES( 'machineB'"
					+ " ,30001 ,1 ,'nameB' ,'machineB' ,30001 ,'zone1')", Machine.class);
			query02.executeUpdate();
			Query query03 = em.createNativeQuery("SELECT no,version,internalUsage,name,"
					+" machineextraInfo_no,machineextraInfo_version,area_Id"
					+" FROM Machine where no='machineB'",Machine.class);
			t =(Machine) query03.getSingleResult();
//			**************************************
			tx.commit();
			
			return findById(t.getPk());
		}catch(Exception e) {
			tx.rollback();
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	
		
	private Machine findById(MachinePK pk) throws Exception  {
		try {
			tx.begin();
			Machine machine = em.find(Machine.class, pk);
			Hibernate.initialize(machine.getMachineWorkingTimes());
			tx.commit();
			return machine;
		} catch (Exception e) {
			tx.rollback();
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	
	
}
