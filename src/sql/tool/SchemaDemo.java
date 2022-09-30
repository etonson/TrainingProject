package sql.tool;

import java.io.File;
import java.util.EnumSet;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.hibernate.tool.schema.TargetType;

import com.practice_03.model.Department;
import com.practice_03.model.Eton;
import com.practice_03.model.Order;
import com.practice_03.model.Person;
import com.practice_03.model.Section;
import com.practice_03.model.WareHouse;

import sql.tool.suport.SettingResource;

public class SchemaDemo{
	final static String UPDATEETABLE_FILE = "D:\\pwd\\updateExport.sql";
	
	public static void main(String[] args) {
		try {
			exportSql();
	        
	        System.out.println("end");
	        System.exit(0);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static void exportSql() 
	{
		SettingResource settingResource = new SettingResource();
		new File(UPDATEETABLE_FILE).delete();

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(settingResource.getSettings()).build();

		MetadataSources metadata = new MetadataSources(serviceRegistry);
		metadata.addAnnotatedClass(Eton.class);
		metadata.addAnnotatedClass(Department.class);
		metadata.addAnnotatedClass(Order.class);
		metadata.addAnnotatedClass(Person.class);
		metadata.addAnnotatedClass(Section.class);
		metadata.addAnnotatedClass(WareHouse.class);
		
		EnumSet<TargetType> enumSet = EnumSet.of(TargetType.DATABASE,TargetType.SCRIPT);
    
		MetadataImplementor metadataImplementor = (MetadataImplementor) metadata.getMetadataBuilder().build();
		SchemaUpdate schemaUpdate = new SchemaUpdate();
		schemaUpdate.setDelimiter(";");
		schemaUpdate.setFormat(true);
		schemaUpdate.setOutputFile(UPDATEETABLE_FILE);
		schemaUpdate.execute(enumSet, metadataImplementor, serviceRegistry);
	} 
}
