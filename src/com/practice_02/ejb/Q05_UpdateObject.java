package com.practice_02.ejb;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.practice_02.support.model.Area;
import com.practice_02.support.model.Area.AreaType;
import com.practice_02.support.remote.UpdateObjectRemote;

@Stateless
public class Q05_UpdateObject extends SuperEJB implements UpdateObjectRemote<Area> {

	private final Log log = LogFactory.getLog(Q05_UpdateObject.class);

	/**
	 * 5-1
	 */
	@Override
	public Area updateByEntityManager(Area t) throws Exception {
		try {
			t.setName("XXX");
			tx.begin();

			em.merge(t);

			tx.commit();

			return findById(t.getID());
		} catch (Exception e) {
			tx.rollback();
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 5-2
	 */
	@Override
	public Integer updateWithNativeSQL(Area t) throws Exception {
		try {
			t.setName("ZZZ");
			tx.begin();
			
			Query query = em.createNativeQuery("UPDATE Area SET name= :name where category = :category ;", Area.class);
			query.setParameter("name", t.getName());
			query.setParameter("category", t.getCategory().toString());
			query.executeUpdate();

			tx.commit();
			Map<String, Object> filter = new HashMap<String, Object>();
			filter.put("name", "ZZZ");
			return findAreas(filter);
		} catch (Exception e) {
			tx.rollback();
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 5-3
	 */
	@Override
	public Integer updateWithCriteria(Area t) throws Exception {
		try {
			tx.begin();
			
	        CriteriaBuilder cb = this.em.getCriteriaBuilder();
	        CriteriaUpdate<Area> update = cb.
	        createCriteriaUpdate(Area.class);
	        Root<Area> root = update.from(Area.class);
	        
	        update.set("category", AreaType.getByName("APS"));
	        update.where(cb.equal(root.get("name"), t.getName()));
		    em.createQuery(update).executeUpdate();
		    
			tx.commit();

			Map<String, Object> filter = new HashMap<String, Object>();
			filter.put("category", "APS");
			return findAreas(filter);
		} catch (Exception e) {
			tx.rollback();
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 5-4
	 */
	@Override
	public Integer updateWithJPQL(Area t) throws Exception {
		try {
			tx.begin();
	        
			CriteriaBuilder cb = this.em.getCriteriaBuilder();
	        CriteriaUpdate<Area> update = cb.
	        createCriteriaUpdate(Area.class);
	        Root<Area> root = update.from(Area.class);
	        update.set("name", "AAT");
	        update.where(cb.equal(root.get("category"), AreaType.getByName("APS")));
		    em.createQuery(update).executeUpdate();

		    tx.commit();

			Map<String, Object> filter = new HashMap<String, Object>();
			filter.put("name", "AAT");
			return findAreas(filter);
		} catch (Exception e) {
			tx.rollback();
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	private Area findById(String id) {
		Area area = em.find(Area.class, id);
		return area;
	}

	private Integer findAreas(Map<String, Object> filter) {
		String name = (String) filter.get("name");
		String category = (String) filter.get("category");

		String ql = "select COUNT(a.ID) from Area a where(1=1) ";

		if (name != null && name != "")
			ql += "and a.name = :name";
		if (category != null && category != "")
			ql += "and a.category = :category";

		Query query = em.createNativeQuery(ql);

		if (name != null && name != "")
			query.setParameter("name", name);
		if (category != null && category != "")
			query.setParameter("category", category);

		Integer o = (Integer) query.getSingleResult();
		return o;
	}

}
