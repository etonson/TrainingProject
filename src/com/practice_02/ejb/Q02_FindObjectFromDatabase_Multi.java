package com.practice_02.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.practice_02.support.model.Area;
import com.practice_02.support.model.Machine;
import com.practice_02.support.model.MachineExtraInfo;
import com.practice_02.support.model.MachinePK;
import com.practice_02.support.remote.FindObjectFromDatabaseRemote;

@Stateless
public class Q02_FindObjectFromDatabase_Multi extends SuperEJB implements FindObjectFromDatabaseRemote<Machine> {

	private final Log log = LogFactory.getLog(Q01_FindObjectFromDatabase.class);

	/**
	 * 2-1 Machine.id: machineE Machine.version: 10001 id="machineE%AAT%10001" ->
	 * 需先做資料分割
	 */
	@Override
	public Machine findByEntityManager(String id) throws Exception {
		Machine machine = new Machine();
		String[] words = id.split("%");
		try {
			tx.begin();
//			**************************************
			// TODO Auto-generated method stub
			MachinePK machinePK = new MachinePK(words[0], Integer.parseInt(words[2]));
			machine.setPk(machinePK);
			machine = em.find(Machine.class, machine.getPk());
//			**************************************
			tx.commit();
			return machine;
		} catch (Exception e) {
			tx.rollback();
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 2-2 filter: {"dayOfWeek" : 2, "dayOfWeekCount" : 3} MachineWorkingTime:
	 * 在星期二(dayOfWeek=2)設定了3筆資料，其他日不限
	 */
//	SELECT no ,version,internalUsage,name,machineextraInfo_no,machineextraInfo_version,area_Id 
//	FROM Machine 
//	where no in (SELECT machine_Id
//	FROM MachineWorkingTime
//	where dayOfWeek = 2
//	GROUP BY MachineWorkingTime.machine_Id,MachineWorkingTime.dayOfWeek
//	having count(*)> 2 )
	@Override
	public List<Machine> findByFilterWithNativeSQL(Map<String, Object> filter) throws Exception {
		List<Machine> machines = new ArrayList<Machine>();
		try {
			tx.begin();
//			**************************************
			// TODO Auto-generated method stub
			Query query = em.createNativeQuery(
					"SELECT no ,version,internalUsage,name,machineextraInfo_no,machineextraInfo_version,area_Id"
							+ " FROM Machine where no in" 
							+ "(SELECT machine_Id" 
							+ " FROM MachineWorkingTime"
							+ " where dayOfWeek = :dayOfWeek"
							+ " GROUP BY MachineWorkingTime.machine_Id,MachineWorkingTime.dayOfWeek"
							+ " having count(*)> :dayOfWeekCount )",
					Machine.class);
			query.setParameter("dayOfWeek", (Integer) filter.get("dayOfWeek"));
			query.setParameter("dayOfWeekCount", (Integer) filter.get("dayOfWeekCount") - 1);

			machines = query.getResultList();
//			**************************************
			tx.commit();
			return machines;
		} catch (Exception e) {
			tx.rollback();
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 2-3 filter: {"machine_name_alias" : "aliasMachineNameR", "internalUsage" :
	 * true, "areaName", "AAA"}
	 */
	@Override
	public List<Machine> findByFilterWithCriteria(Map<String, Object> filter) throws Exception {
		List<Machine> machines = new ArrayList<Machine>();
		try {
			String machine_name_alias = (String) filter.get("machine_name_alias");
			String areaName = (String) filter.get("areaName");
			Boolean internalUsage = (Boolean) filter.get("internalUsage");
			tx.begin();
//			**************************************
			// TODO Auto-generated method stub
			List<Predicate> predicateList = new ArrayList<Predicate>();
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<Machine> criteriaQuery = criteriaBuilder.createQuery(Machine.class);
			Root<Machine> rootMachine = criteriaQuery.from(Machine.class);
															
			Join<Machine, Area> joinArea = rootMachine.join("region", JoinType.LEFT);
			joinArea.on(criteriaBuilder.equal(joinArea.get("ID"), rootMachine.get("region").get("ID")));

			Join<Machine, MachineExtraInfo> joinMachineExtraInfo = rootMachine.join("machineextraInfo", JoinType.LEFT);
			joinMachineExtraInfo.on(criteriaBuilder.equal(joinMachineExtraInfo.get("machinepk").get("id"),
					rootMachine.get("machineextraInfo").get("machinepk").get("id")));
			
			predicateList.add(criteriaBuilder.equal(joinMachineExtraInfo.get("machine_name_alias"),machine_name_alias));
			predicateList.add(criteriaBuilder.equal(joinArea.get("name"), areaName));
			predicateList.add(criteriaBuilder.equal(rootMachine.get("internalUsage"), internalUsage));
			
			Predicate[] predicates = new Predicate[predicateList.size()];
			predicateList.toArray(predicates);
			criteriaQuery.where(criteriaBuilder.and(predicates));
			TypedQuery<Machine> typedQuery = em.createQuery(criteriaQuery);
			machines = typedQuery.getResultList();
//			**************************************
			tx.commit();
			return machines;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}

	}

	/**
	 * 2-4 filter: {"machine_name_alias" : "aliasMachineNameQ", "machineWorkingTime"
	 * : 3, "name", "AAA"}
	 */
	@Override
	public List<Machine> findByFilterJPQL(Map<String, Object> filter) throws Exception {
		List<Machine> machines = new ArrayList<Machine>();
		String machineNameAlias =(String) filter.get("machine_name_alias");
		String areaName =(String) filter.get("name");
		int machineWorkingTime =(Integer) filter.get("machineWorkingTime");
		try {
			tx.begin();
//			**************************************
			// TODO Auto-generated method stub
//			Query query = em.createNativeQuery(
//					"SELECT Machine.no"
//					+ ",Machine.version"
//					+ ",Machine.internalUsage"
//					+ ",Machine.name"
//					+ ",Machine.machineextraInfo_no"
//					+ ",Machine.machineextraInfo_version"
//					+ ",Machine.area_Id"
//					+ " FROM Machine"
//					+ " LEFT JOIN area on Area.ID=Machine.area_Id"
//					+ " LEFT JOIN MachineExtraInfo on MachineExtraInfo.no=Machine.no"
//					+ " where Machine.no in (SELECT MachineWorkingTime.machine_Id"
//					+ " FROM MachineWorkingTime"
//					+ " GROUP BY MachineWorkingTime.machine_Id"
//					+ " having count(*)= :machineWorkingTime )"
//					+ " and Area.name= :areaName"
//					+ " and MachineExtraInfo.machine_name_alias= :machineNameAlias"
//					,Machine.class);
//			query.setParameter("areaName",areaName);
//			query.setParameter("machineNameAlias", machineNameAlias);
//			query.setParameter("machineWorkingTime", machineWorkingTime);
			Query query = em.createNamedQuery("findByFilterJPQL2",Machine.class);
			query.setParameter("areaName",areaName);
			query.setParameter("machineNameAlias", machineNameAlias);
			query.setParameter("machineWorkingTime", machineWorkingTime);
			
			machines = query.getResultList();
//			machines = query.getResultList();

//			**************************************
			tx.commit();
			return machines;
		} catch (Exception e) {
			tx.rollback();
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

}
