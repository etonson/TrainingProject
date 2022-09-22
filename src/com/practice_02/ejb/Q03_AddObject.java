package com.practice_02.ejb;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.practice_02.support.model.Area;
import com.practice_02.support.model.Area.AreaType;
import com.practice_02.support.remote.AddObjectRemote;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class Q03_AddObject extends SuperEJB implements AddObjectRemote<Area> {

	private final Log log = LogFactory.getLog(Q03_AddObject.class);

	public Q03_AddObject() {
		super();
	}

	/**
	 * 3-1 id: areaA name: nameA category: APS
	 */
	@Override
	public Area addByEntityManager(Area t) throws Exception {
		try {
			t.setID("areaA");
			t.setName("nameA");
			t.setCategory(AreaType.APS);
			tx.begin();

			em.persist(t);

			tx.commit();

			return findById(t.getID());
		} catch (Exception e) {
			tx.rollback();
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 3-2 id: areaB name: nameB category: WMS
	 */
	@Override
	public Area addWithNativeSQL(Area t) throws Exception {
		try {
			tx.begin();
			t.setID("areaB");
			t.setName("nameB");
			t.setCategory(AreaType.WMS);

			Query query = em.createNativeQuery("insert into Area (ID,category,name)values (:id,:category,:name);",Area.class);
			query.setParameter("id", t.getID());
			query.setParameter("category", t.getCategory().toString());
			query.setParameter("name", t.getName());
			query.executeUpdate();

			tx.commit();

			return findById(t.getID());
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

}
