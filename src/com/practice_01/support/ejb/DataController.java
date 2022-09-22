package com.practice_01.support.ejb;

import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import com.practice_01.support.model.MachineResourceUsageSetting;
import com.practice_01.support.model.MachineX;
import com.practice_01.support.model.ProcessStage;
import com.practice_01.support.model.Resource;
import com.practice_01.support.model.ResourceUsageSettingPK;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DataController implements IDataController {
	@PersistenceContext(unitName = "jpa-iPASP-StageDB")
	protected EntityManager em;

	@Override
	@Transactional
	public void addControl(MachineResourceUsageSetting t) throws Exception {
		em.persist(t);
	}

	@Override
	@Transactional
	public void updateControl(Resource t) throws Exception {
		Resource dbr = em.find(Resource.class, t.getId());
		dbr.setName(t.getName());
		em.merge(dbr);
	}

	@Override
	@Transactional
	public ProcessStage retriveControl(Map<String, Object> filter) throws Exception {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ProcessStage> cq = cb.createQuery(ProcessStage.class);
		Root<ProcessStage> root = cq.from(ProcessStage.class);
		cq.where(cb.and(cb.equal(root.get("name"), filter.getOrDefault("name", "")),
				cb.equal(root.get("sequence"), filter.getOrDefault("sequence", ""))));
		return em.createQuery(cq).getSingleResult();
	}

	@Override
	@Transactional
	public void deleteControl(MachineX t) throws Exception {
               Query cq = em.createQuery("DELETE FROM  MachineX x WHERE  x.name = :name") ; 
               cq.setParameter("name", t.getName()); 
               cq.executeUpdate();
	}

	@Override
	public MachineX retriveControl(String machinexname) throws Exception {

		return em.find(MachineX.class, machinexname);
	}

	@Override
	public MachineResourceUsageSetting validateAddControl() throws Exception {
		return em.find(MachineResourceUsageSetting.class, new ResourceUsageSettingPK("MONK", 5));
	}

	@Override
	public Resource validateUpdateControl() throws Exception {
		return em.find(Resource.class, "resource2");
	}

	@Override
	public MachineX validateDeleteControl() throws Exception {
		return em.find(MachineX.class, "machine3");
	}

}
