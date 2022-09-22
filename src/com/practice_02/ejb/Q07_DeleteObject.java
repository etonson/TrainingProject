package com.practice_02.ejb;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.practice_02.support.model.Area;
import com.practice_02.support.model.Area.AreaType;
import com.practice_02.support.remote.DeleteObjectRemote;

@Stateless
public class Q07_DeleteObject extends SuperEJB implements DeleteObjectRemote<Area> {
	
	private final Log log = LogFactory.getLog(Q07_DeleteObject.class);
	
	/**
	 * 7-1
	 */
	@Override
	public Integer deleteByEntityManager(Area t) throws Exception {
		try {
			tx.begin();
			// TODO Auto-generated method stub
			em.remove(em.merge(t));
			
			tx.commit();
			Map<String, Object> filter = new HashMap<String, Object>();
			filter.put("id", "zone1");
			return findAreas(filter);
		}catch(Exception e) {
			tx.rollback();
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 7-2
	 */
	@Override
	public Integer deleteWithNativeSQL(Area t) throws Exception {
		try {
			tx.begin();
			
			Query query = em.createNativeQuery("DELETE FROM Area WHERE category = :category ;", Area.class);
			query.setParameter("category", t.getCategory().toString());
			query.executeUpdate();
			
			tx.commit();
			
			Map<String, Object> filter = new HashMap<String, Object>();
			filter.put("category", "WMS");
			return findAreas(filter);
		}catch(Exception e) {
			tx.rollback();
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 7-3
	 */
	@Override
	public Integer deleteWithCriteria(Area t) throws Exception {
		try {
			tx.begin();
	        CriteriaBuilder cb = this.em.getCriteriaBuilder();
	        CriteriaDelete<Area> delete = cb.createCriteriaDelete(Area.class);
	        Root<Area> root = delete.from(Area.class);
	        delete.where(cb.equal(root.get("name"), t.getName()));
		    em.createQuery(delete).executeUpdate();
			tx.commit();
			
			Map<String, Object> filter = new HashMap<String, Object>();
			filter.put("name", "AAA");
			return findAreas(filter);
		}catch(Exception e) {
			tx.rollback();
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 7-4
	 */
	@Override
	public Integer deleteWithJPQL(Area t) throws Exception {
		try {
			tx.begin();

			CriteriaBuilder cb = this.em.getCriteriaBuilder();
			CriteriaDelete<Area> delete = cb.createCriteriaDelete(Area.class);
			Root<Area> root = delete.from(Area.class);
			delete.where(cb.equal(root.get("category"), AreaType.getByName(t.getCategory().toString())));
			em.createQuery(delete).executeUpdate();

			tx.commit();
			
			Map<String, Object> filter = new HashMap<String, Object>();
			filter.put("category", "APS");
			return findAreas(filter);
		}catch(Exception e) {
			tx.rollback();
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	
	
	
	private Integer findAreas(Map<String, Object> filter) {
		String id = (String) filter.get("id");
		String name = (String) filter.get("name");
		String category = (String) filter.get("category");
		
		String ql = "select COUNT(a.ID) from Area a where(1=1) ";
		
		if(id != null && id != "")
			ql += "and a.ID = :id";
		if(name != null && name != "")
			ql += "and a.name = :name";
		if(category != null && category != "")
			ql += "and a.category = :category";
		
        Query query = em.createNativeQuery(ql);
        
        if(id != null && id != "")
        	query.setParameter("id", id);
        if(name != null && name != "")
        	query.setParameter("name", name);
        if(category != null && category != "")
        	query.setParameter("category", category);
        
        Integer o = (Integer) query.getSingleResult();
        return o;
	} 
	
}
