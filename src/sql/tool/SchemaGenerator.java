package sql.tool;

import java.io.File;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaExport.Action;
import org.hibernate.tool.schema.TargetType;

import com.practice_03.model.Department;
import com.practice_03.model.Eton;
import com.practice_03.model.Order;
import com.practice_03.model.Person;
import com.practice_03.model.Section;
import com.practice_03.model.WareHouse;

public class SchemaGenerator {
	public static void main(String[] args) {
		try {

			final String FILE = "D:\\pwd\\export.sql";
			new File(FILE).delete();
			Map<String, String> settings = new HashMap<>();
			settings.put("connection.driver_class", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
			settings.put("dialect", "org.hibernate.dialect.SQLServer2012Dialect");
			settings.put("hibernate.connection.url", "jdbc:sqlserver://localhost:1433;databaseName=PracticeDB");
			settings.put("tomee.jpa.cdi","false");
			settings.put("transaction.factory_class","org.hibernate.transaction.JTATransactionFactory");
			settings.put("hibernate.connection.username", "sa");
			settings.put("hibernate.connection.password", "six983son410");
			settings.put("hibernate.hbm2ddl.auto", "update");
			settings.put("hibernate.show_sql", "true");
			settings.put("hibernate.format_sql", "true");

			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(settings).build();

			MetadataSources metadata = new MetadataSources(serviceRegistry);
			metadata.addAnnotatedClass(Eton.class);
			metadata.addAnnotatedClass(Department.class);
			metadata.addAnnotatedClass(Order.class);
			metadata.addAnnotatedClass(Person.class);
			metadata.addAnnotatedClass(Section.class);
			metadata.addAnnotatedClass(WareHouse.class);
			
			EnumSet<TargetType> enumSet = EnumSet.of(TargetType.DATABASE,TargetType.SCRIPT);
	        SchemaExport export= new SchemaExport();
	        export.setDelimiter(";");
	        export.setFormat(true);
	        export.setOutputFile(FILE);
	        export.execute(enumSet,Action.BOTH,metadata.buildMetadata(),serviceRegistry );
	        
	        
	        System.out.println("end");
	        System.exit(0);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
