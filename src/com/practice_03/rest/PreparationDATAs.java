package com.practice_03.rest;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaExport.Action;
import org.hibernate.tool.schema.TargetType;

import com.practice_03.model.Department;
import com.practice_03.model.Order;
import com.practice_03.model.Order.OrderStatus;
import com.practice_03.model.Person;
import com.practice_03.model.Section;
import com.practice_03.model.SectionPK;
import com.practice_03.model.WareHouse;
import com.practice_03.remote.IDepartment;
import com.practice_03.remote.IOrder;
import com.practice_03.remote.IPerson;
import com.practice_03.remote.ISection;
import com.practice_03.remote.ISectionPK;
import com.practice_03.remote.IWareHouse;
import com.tool.JsoupWrapper;

@Path("httpprepareData")
public class PreparationDATAs {
	JsoupWrapper jsoup = new JsoupWrapper();

	@EJB
	IPerson iPerson;
	@EJB
	IDepartment iDepartment;
	@EJB
	ISection iSection;
	@EJB
	ISectionPK iSectionPK;
	@EJB
	IWareHouse iWareHouse;
	@EJB
	IOrder iOrder;

	@Path("createData")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public String preparePersonData(String str) {
		System.out.println(str);
		try {
			Person person01 = new Person(1, "eton");
			Person person02 = new Person(2, "joe");
			Person person03 = new Person(3, "john");
			Person person04 = new Person(4, "bill");
			Person person05 = new Person(5, "alice");

			// ---department
			Department department01 = new Department("RD");
			Department department02 = new Department("IT");

			// ---section
			SectionPK sectionPK01 = new SectionPK("RD", "section01");
			SectionPK sectionPK02 = new SectionPK("IT", "section02");
			SectionPK sectionPK03 = new SectionPK("IT", "section03");
			Section section01 = new Section(sectionPK01, person01);
			Section section02 = new Section(sectionPK02, person02);
			Section section03 = new Section(sectionPK03, person05);


			List<Section> sections = new ArrayList<Section>();
			sections.add(section01);
			sections.add(section02);
			sections.add(section03);
;

			department01.setSections(sections);
			department01.setManager(person01);
			department02.setManager(person04);

			iPerson.doCreate(person01);
			iPerson.doCreate(person02);
			iPerson.doCreate(person03);
			iPerson.doCreate(person04);
			iPerson.doCreate(person05);

			iDepartment.doCreate(department01);
			iDepartment.doCreate(department02);
			iSection.doCreate(section01);
			iSection.doCreate(section02);
			iSection.doCreate(section03);


			Order order01 = new Order("order01", 520, OrderStatus.FG);
			Order order03 = new Order("order03", 520, OrderStatus.WIP);
			Order order05 = new Order("order05", 520, OrderStatus.DEDICATED);
			Order order02 = new Order("order02", 520, OrderStatus.FG);
			Order order04 = new Order("order04", 520, OrderStatus.WIP);
			Order order06 = new Order("order06", 520, OrderStatus.DEDICATED);

			order02.setOwnSections(sections);
			List<Order> orders01 = new ArrayList<Order>();
			orders01.add(order01);
			orders01.add(order03);
			orders01.add(order05);
			orders01.add(order05);
			
			List<Order> orders02 = new ArrayList<Order>();
			orders02.add(order02);
			orders02.add(order04);
			orders02.add(order06);

			WareHouse wareHouse01 = new WareHouse("WareHouses01");
			WareHouse wareHouse02 = new WareHouse("WareHouses02");
			WareHouse wareHouse03 = new WareHouse("WareHouses03");
			WareHouse wareHouse04 = new WareHouse("WareHouses04");
			WareHouse wareHouse05 = new WareHouse("WareHouses05");
			WareHouse wareHouse06 = new WareHouse("WareHouses06");
			wareHouse06.setOrders(orders01);

			iWareHouse.doCreate(wareHouse01);
			iWareHouse.doCreate(wareHouse02);
			iWareHouse.doCreate(wareHouse03);
			iWareHouse.doCreate(wareHouse04);
			iWareHouse.doCreate(wareHouse05);
			iWareHouse.doCreate(wareHouse06);

			iOrder.doCreate(order01);
			iOrder.doCreate(order02);
			iOrder.doCreate(order03);
			iOrder.doCreate(order04);
			iOrder.doCreate(order05);
			iOrder.doCreate(order06);

			section01.setOwnOrders(orders01);
			section02.setOwnOrders(orders02);
			iSection.doMergeOrder(section01);
			iSection.doMergeOrder(section02);
		} catch (Exception e) {
			e.fillInStackTrace();
		}
		System.out.println("prepare data ok!");
		return "prepare data ok!";
	}
}