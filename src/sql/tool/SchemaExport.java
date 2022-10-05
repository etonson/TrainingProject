package sql.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.hibernate.tool.schema.TargetType;

import sql.tool.suport.AccessingAllClassesInPackage;
import sql.tool.suport.SQLStatementDeal;
import sql.tool.suport.SchemaFileDeal;
import sql.tool.suport.SettingResource;

public class SchemaExport {
	final static String UPDATEETABLE_FILE = "D:\\pwd\\updateExport.sql";
	final static String FINAL_SQL_FILE = "D:\\pwd\\schema.sql";
	final static String PACKAGE_PATH = "com.practice_03.model";

	public static void main(String[] args) {
		try {
			List<String> array = new ArrayList<String>();
			exportSql();
			array = readSQL();
			writerSQL(array);
			System.out.println("end");
			System.exit(0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void exportSql() {
		SettingResource settingResource = new SettingResource();
		new File(UPDATEETABLE_FILE).delete();

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(settingResource.getSettings()).build();
		
		Set<Class<?>> entitySet = new HashSet<Class<?>>();
		AccessingAllClassesInPackage a1 = new AccessingAllClassesInPackage();
		entitySet = a1.findAllClassesUsingClassLoader(PACKAGE_PATH);
		
		Iterator iterator = entitySet.iterator();
		MetadataSources metadata = new MetadataSources(serviceRegistry);
		while(iterator.hasNext()) {
		metadata.addAnnotatedClass((Class<?>)iterator.next());
		}
		EnumSet<TargetType> enumSet = EnumSet.of(TargetType.SCRIPT);

		MetadataImplementor metadataImplementor = (MetadataImplementor) metadata.getMetadataBuilder().build();
		SchemaUpdate schemaUpdate = new SchemaUpdate();
		schemaUpdate.setDelimiter(";");
		schemaUpdate.setFormat(true);
		schemaUpdate.setOutputFile(UPDATEETABLE_FILE);
		schemaUpdate.execute(enumSet, metadataImplementor, serviceRegistry);
	}

	public static List<String> readSQL() {
		List<String> exportSqlSchema = null;
		try {
			exportSqlSchema = new ArrayList<String>();
			String sqlList = SchemaFileDeal.loadSql(UPDATEETABLE_FILE);
			SQLStatementDeal sd = new SQLStatementDeal();
			String[] sqlArray = sd.sqlQuery2Array(sqlList);
			for (int i = 0; i < sqlArray.length; i++) {
				String tmpStr = sd.removeSpecialChar(sqlArray[i]);
				if (sd.isCreatedClass(tmpStr)) {
					exportSqlSchema.add(sd.removeCons(tmpStr));
				}
				if (sd.isAddUpdatedColumn(tmpStr) == true && sd.isAddReferencesdColumnClass(tmpStr) == false) {
					exportSqlSchema.add(sd.removeCons(tmpStr));
				}
			}
			return exportSqlSchema;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void writerSQL(List<String> exportSqlSchema) {
		try {
			BufferedWriter f_writer = new BufferedWriter(new FileWriter(FINAL_SQL_FILE));
			for (int i = 0; i < exportSqlSchema.size(); i++) {
				f_writer.write(exportSqlSchema.get(i) + "\n");
			}
			f_writer.flush();
			f_writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
