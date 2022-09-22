package com.practice_02.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.practice_02.support.model.Area;
import com.practice_02.support.model.Area.AreaType;
import com.practice_02.support.remote.FindObjectFromDatabaseRemote;
@Stateless
public class Q01_FindObjectFromDatabase extends SuperEJB implements FindObjectFromDatabaseRemote<Area>{

	private final Log log = LogFactory.getLog(Q01_FindObjectFromDatabase.class);
	
	/**
	 * 1-1 使用 em.find()
	 */
	@Override
	public Area findByEntityManager(String id) throws Exception {
		Area area = new Area();
		
		try {
			tx.begin();
//			**************************************
			// TODO Auto-generated method stub
			area.setID(id);
			area = em.find(Area.class, area.getID());
//			**************************************
			tx.commit();
			return area;
		}catch(Exception e) {
			tx.rollback();
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 1-2 filter: {"name" : "ABC"}
	 */
	@Override
	public List<Area> findByFilterWithNativeSQL(Map<String, Object> filter) throws Exception {

		try {		
			List<Area> datas = new ArrayList<Area>() ;
			tx.begin();
//			**************************************
			// TODO Auto-generated method stub
			Query query = em.createNativeQuery("SELECT ID,category,name FROM Area where name = :name",Area.class);
			query.setParameter("name", (String)filter.get("name"));
			datas = query.getResultList();
//			**************************************
			tx.commit();
        	return datas;
		}catch(Exception e) {
			tx.rollback();
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
        
	}

	/**
	 * 1-3 filter: {"name" : "AAA", "category" : "WMS"}
	 */
	@Override
	public List<Area> findByFilterWithCriteria(Map<String, Object> filter) throws Exception {
		List<Area> datas = new ArrayList<>();
		try {
			tx.begin();
//			**************************************
			// TODO Auto-generated method stub
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<Area> criteriaQuery = criteriaBuilder.createQuery(Area.class);
			Root<Area> from = criteriaQuery.from(Area.class);
			Predicate p1 = criteriaBuilder.equal(from.get("category"), AreaType.getByName((String)filter.get("category")));
			Predicate p2 = criteriaBuilder.equal(from.get("name"), filter.get("name"));
			Predicate p = criteriaBuilder.and(p1,p2); 
		    criteriaQuery.where(p);
			TypedQuery<Area> typedQuery = em.createQuery(criteriaQuery);
		    datas = typedQuery.getResultList();
//			**************************************
			tx.commit();
			return datas;
		}catch(Exception e) {
			tx.rollback();
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 1-4 filter: {"name" : "AAA", "category" : "PHYSICAL"}
	 */
	@Override
	public List<Area> findByFilterJPQL(Map<String, Object> filter) throws Exception {
		List<Area> datas = new ArrayList<>();
		try {
			tx.begin();
//			**************************************
			// TODO Auto-generated method stub
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<Area> criteriaQuery = criteriaBuilder.createQuery(Area.class);
			Root<Area> from = criteriaQuery.from(Area.class);
			Predicate p1 = criteriaBuilder.equal(from.get("category"), AreaType.getByName((String)filter.get("category")));
			Predicate p2 = criteriaBuilder.equal(from.get("name"), filter.get("name"));
			Predicate p = criteriaBuilder.and(p1,p2); 
		    criteriaQuery.where(p);
			TypedQuery<Area> typedQuery = em.createQuery(criteriaQuery);
		    datas = typedQuery.getResultList();
//			**************************************
			tx.commit();
        
			return datas;
		}catch(Exception e) {
			tx.rollback();
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
}
