package com.practice_02.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import com.practice_02.support.model.Area;
import com.practice_02.support.model.Machine;
import com.practice_02.support.model.MachineExtraInfo;
import com.practice_02.support.model.MachinePK;
import com.practice_02.support.model.MachineWorkingTime;
import com.practice_02.support.remote.UpdateObjectRemote2;

@Stateless	
public class Q06_UpdateObject_Multi extends SuperEJB implements UpdateObjectRemote2<Machine>{

	private final Log log = LogFactory.getLog(Q06_UpdateObject_Multi.class);
	
	/**
	 * 6-1
	 */
	@Override
	public Machine updateByEntityManager(Machine t) throws Exception {
		try {
			tx.begin();
//			**************************************
			// TODO Auto-generated method stub
			t = em.find(Machine.class,t.getPk());
			Area area = new Area("zone2");
			t.setRegion(area);
			List<MachineWorkingTime> listMachineWorkingTimes = new ArrayList<MachineWorkingTime>();
			listMachineWorkingTimes = t.getMachineWorkingTimes();
			for(int i=1;i<=listMachineWorkingTimes.size();i++) 
			{
				listMachineWorkingTimes.get(i-1).setId(listMachineWorkingTimes.get(i-1).getId());
				listMachineWorkingTimes.get(i-1).setDayOfWeek(i);
				listMachineWorkingTimes.get(i-1).setStartHour(9);
				listMachineWorkingTimes.get(i-1).setStartMinutes(0);
				listMachineWorkingTimes.get(i-1).setEndHour(18);
				listMachineWorkingTimes.get(i-1).setEndMinutes(0);
			}
			t.setMachineWorkingTimes(listMachineWorkingTimes);
			em.merge(t);
			

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
	 * 6-2
	 */
	@Override
	public Machine updateWithNativeSQL(Machine t) throws Exception {
		try {
			tx.begin();
//			**************************************
			// TODO Auto-generated method stub
			Query query01 = em.createNativeQuery(
					"UPDATE Machine"
					+ " SET internalUsage=0"
					+ " ,name = 'machineNameQQQ'"
					+ " WHERE no='machineC'",
					Machine.class);
			query01.executeUpdate();
			
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
	 * 6-3
	 */
	@Override
	public Machine updateWithCriteria(Machine t) throws Exception {
		try {
			tx.begin();
//			**************************************
			// TODO Auto-generated method stub
	        CriteriaBuilder cb = this.em.getCriteriaBuilder();
	        CriteriaUpdate<MachineExtraInfo> updateExtraInfo = cb.
	        createCriteriaUpdate(MachineExtraInfo.class);
	        Root<MachineExtraInfo> machineExtraInfoRoot = updateExtraInfo.from(MachineExtraInfo.class);
	        
	        updateExtraInfo.set("machine_name_alias", "aliasMachineNameRRR");
	        updateExtraInfo.where(cb.equal(machineExtraInfoRoot.get("machinepk").get("id"), "machineF"));
		    em.createQuery(updateExtraInfo).executeUpdate();
		    
	        CriteriaBuilder cb2 = this.em.getCriteriaBuilder();
	        CriteriaUpdate<Machine> updateMachine = cb2.
	        createCriteriaUpdate(Machine.class);
	        Root<Machine> machineRoot = updateMachine.from(Machine.class);
	        
	        updateMachine.set("name", "machineNameQQQ");
	        updateMachine.where(cb2.equal(machineRoot.get("pk").get("id"), "machineF"));
		    em.createQuery(updateMachine).executeUpdate();
		    
		    
			
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
	 * 6-4
	 */
	@Override
	public Machine updateWithJPQL(Machine t) throws Exception {
		try {
			tx.begin();
//			**************************************
			// TODO Auto-generated method stub
			Query query01 = em.createNativeQuery(
					"UPDATE Machine"
					+ " SET area_Id = 'zone4'"
					+ " ,machineextraInfo_no = null"
					+ " ,machineextraInfo_version=null"
					+ " WHERE no='machineD'",
					Machine.class);
			query01.executeUpdate();
			
			Query query02 = em.createNativeQuery(
					" UPDATE MachineWorkingTime "
					+ " SET machine_Id = null "
					+ " ,machine_Ver = null "
					+ " WHERE machine_Id='machineD'",
					Machine.class);
			query02.executeUpdate();
//			**************************************
			tx.commit();
			
			return findById(t.getPk());
		}catch(Exception e) {
			tx.rollback();
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	
	
	private Machine findById(MachinePK pk) {
		try {
			tx.begin();
			Machine machine = em.find(Machine.class, pk);
			Hibernate.initialize(machine.getMachineWorkingTimes());
			tx.commit();
			return machine;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	
}
