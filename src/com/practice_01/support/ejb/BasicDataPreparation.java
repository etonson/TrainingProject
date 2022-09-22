package com.practice_01.support.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.practice_01.support.model.MachineResourceUsageSetting;
import com.practice_01.support.model.MachineX;
import com.practice_01.support.model.ProcessStage;
import com.practice_01.support.model.Resource;
import com.practice_01.support.model.ResourceUsageSettingPK;
import com.practice_02.support.model.Area;
import com.practice_02.support.model.Area.AreaType;
import com.practice_02.support.model.Machine;
import com.practice_02.support.model.MachineExtraInfo;
import com.practice_02.support.model.MachinePK;
import com.practice_02.support.model.MachineWorkingTime;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BasicDataPreparation implements IBasicDataPreparation {
	@PersistenceContext(unitName = "jpa-iPASP-StageDB")
	protected EntityManager em;

	@Transactional
	public void prepareBasicData() {
		    ProcessStage ps1 = new ProcessStage("PS1");
		    ps1.setName("STE");
		    ps1.setSequence(1);
		em.persist(ps1);
		em.persist(new ProcessStage("PS2"));
		MachineX machine1 = new MachineX("machine1");
		machine1.setBrand("AAT");
		em.persist(machine1);
		em.persist(new MachineX("machine2"));
		em.persist(new MachineX("machine3"));
		em.persist(new Resource("r1", "測試資源"));
		em.persist(new Resource("resource2", "資源二號"));
		MachineResourceUsageSetting mrus = new MachineResourceUsageSetting(
				new ResourceUsageSettingPK("rus1", Integer.valueOf(1)));
		mrus.setMachine((MachineX) em.getReference(MachineX.class, "machine1"));
		mrus.setResource((com.practice_01.support.model.Resource) em
				.getReference(com.practice_01.support.model.Resource.class, "r1"));
		List<ProcessStage> list = new ArrayList<ProcessStage>();
		list.add((ProcessStage) em.getReference(ProcessStage.class, "PS1"));
		mrus.setStages(list);
		em.persist(mrus);
	}

	@Transactional
	public void cleanData() {
		em.createQuery("DELETE  FROM  MachineResourceUsageSetting C ").executeUpdate();
		em.createQuery("DELETE  FROM  MachineX C ").executeUpdate();
		em.createQuery("DELETE  FROM  Resource C ").executeUpdate();
		em.createQuery("DELETE  FROM  ProcessStage C ").executeUpdate();
	}

	@Transactional
	public void practice_02_initinalData() {
		em.persist(new Area("zone1", "AAA", AreaType.APS));
		em.persist(new Area("zone2", "ABC", AreaType.WMS));
		em.persist(new Area("zone3", "AAA", AreaType.WMS));
		em.persist(new Area("zone4", "AAA", AreaType.PHYSICAL));
	}

	@Transactional
	public void practice_02_resetData() {
		em.createQuery("DELETE FROM Area a ").executeUpdate();
	}
	
	@Transactional
	public void practice_02_initinalDataMachineWorkingTime() {
		em.persist(new MachineWorkingTime(11, (Machine) em.getReference(Machine.class, new MachinePK("machineA", 10001)), 1));
		em.persist(new MachineWorkingTime(12, (Machine) em.getReference(Machine.class, new MachinePK("machineA", 10001)), 2));
		em.persist(new MachineWorkingTime(13, (Machine) em.getReference(Machine.class, new MachinePK("machineA", 10001)), 3));
		
		em.persist(new MachineWorkingTime(21, (Machine) em.getReference(Machine.class, new MachinePK("machineB", 10002)), 1));
		em.persist(new MachineWorkingTime(22, (Machine) em.getReference(Machine.class, new MachinePK("machineB", 10002)), 2));
		em.persist(new MachineWorkingTime(23, (Machine) em.getReference(Machine.class, new MachinePK("machineB", 10002)), 3));
		
		em.persist(new MachineWorkingTime(31, (Machine) em.getReference(Machine.class, new MachinePK("machineC", 10002)), 1));
		em.persist(new MachineWorkingTime(32, (Machine) em.getReference(Machine.class, new MachinePK("machineC", 10002)), 2));
		em.persist(new MachineWorkingTime(33, (Machine) em.getReference(Machine.class, new MachinePK("machineC", 10002)), 2));
		em.persist(new MachineWorkingTime(34, (Machine) em.getReference(Machine.class, new MachinePK("machineC", 10002)), 2));
		em.persist(new MachineWorkingTime(35, (Machine) em.getReference(Machine.class, new MachinePK("machineC", 10002)), 4));
		
		em.persist(new MachineWorkingTime(41, (Machine) em.getReference(Machine.class, new MachinePK("machineD", 10001)), 1));
		em.persist(new MachineWorkingTime(42, (Machine) em.getReference(Machine.class, new MachinePK("machineD", 10001)), 2));
		em.persist(new MachineWorkingTime(43, (Machine) em.getReference(Machine.class, new MachinePK("machineD", 10001)), 2));
		em.persist(new MachineWorkingTime(44, (Machine) em.getReference(Machine.class, new MachinePK("machineD", 10001)), 2));
		
		em.persist(new MachineWorkingTime(51, (Machine) em.getReference(Machine.class, new MachinePK("machineE", 10001)), 1));
		em.persist(new MachineWorkingTime(52, (Machine) em.getReference(Machine.class, new MachinePK("machineE", 10001)), 2));
		em.persist(new MachineWorkingTime(53, (Machine) em.getReference(Machine.class, new MachinePK("machineE", 10001)), 3));
		
		em.persist(new MachineWorkingTime(71, (Machine) em.getReference(Machine.class, new MachinePK("machineG", 10001)), 1));
		em.persist(new MachineWorkingTime(72, (Machine) em.getReference(Machine.class, new MachinePK("machineG", 10001)), 2));
		em.persist(new MachineWorkingTime(73, (Machine) em.getReference(Machine.class, new MachinePK("machineG", 10001)), 3));
		em.persist(new MachineWorkingTime(74, (Machine) em.getReference(Machine.class, new MachinePK("machineG", 10001)), 4));
	}
	
	
	@Transactional
	public void practice_02_initinalDataMachine() {
		
		practice_02_initinalData(); 
		
		MachinePK pk = new MachinePK("machineA", 10001);
		Machine machineA = new Machine(pk);
		machineA.setName("machineNameQ");
		machineA.setRegion((Area) em.getReference(Area.class, "zone1"));
		machineA.setInternalUsage(true);
		machineA.setMachineextraInfo(new MachineExtraInfo(pk, "aliasMachineNameQ"));
		em.persist(machineA);
		
		MachinePK pk2 = new MachinePK("machineB", 10002);
		Machine machineB = new Machine(pk2);
		machineB.setName("machineNameQ");
		machineB.setRegion((Area) em.getReference(Area.class, "zone1"));
		machineB.setInternalUsage(false);
		machineB.setMachineextraInfo(new MachineExtraInfo(pk2, "aliasMachineNameQ"));
		em.persist(machineB);
		
		MachinePK pk3 = new MachinePK("machineC", 10002);
		Machine machineC = new Machine(pk3);
		machineC.setName("machineNameQ");
		machineC.setRegion((Area) em.getReference(Area.class, "zone2"));
		machineC.setInternalUsage(true);
		machineC.setMachineextraInfo(new MachineExtraInfo(pk3, "aliasMachineNameQ"));
		em.persist(machineC);
		
		MachinePK pk4 = new MachinePK("machineD", 10001);
		Machine machineD = new Machine(pk4);
		machineD.setName("machineNameR");
		machineD.setRegion((Area) em.getReference(Area.class, "zone2"));
		machineD.setInternalUsage(true);
		machineD.setMachineextraInfo(new MachineExtraInfo(pk4, "aliasMachineNameR"));
		em.persist(machineD);
		
		MachinePK pk5 = new MachinePK("machineE", 10001);
		Machine machineE = new Machine(pk5);
		machineE.setName("machineNameR");
		machineE.setRegion((Area) em.getReference(Area.class, "zone3"));
		machineE.setInternalUsage(false);
		machineE.setMachineextraInfo(new MachineExtraInfo(pk5, "aliasMachineNameR"));
		em.persist(machineE);
		
		MachinePK pk6 = new MachinePK("machineF", 10001);
		Machine machineF = new Machine(pk6);
		machineF.setName("machineNameR");
		machineF.setRegion((Area) em.getReference(Area.class, "zone4"));
		machineF.setInternalUsage(true);
		machineF.setMachineextraInfo(new MachineExtraInfo(pk6, "aliasMachineNameR"));
		em.persist(machineF);
		
		MachinePK pk7 = new MachinePK("machineG", 10001);
		Machine machineG = new Machine(pk7);
		machineG.setName("machineNameQ");
		machineG.setRegion((Area) em.getReference(Area.class, "zone1"));
		machineG.setInternalUsage(false);
		machineG.setMachineextraInfo(new MachineExtraInfo(pk7, "aliasMachineNameQ"));
		em.persist(machineG);
		
		practice_02_initinalDataMachineWorkingTime();
		
	}
	
	@Transactional
	public void practice_02_resetDataMachine() {
		em.createQuery("DELETE FROM MachineWorkingTime a ").executeUpdate();
		em.createQuery("DELETE FROM Machine a ").executeUpdate();
		em.createQuery("DELETE FROM MachineExtraInfo a ").executeUpdate();
		practice_02_resetData();
	}
	
	
}