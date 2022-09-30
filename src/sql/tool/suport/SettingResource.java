package sql.tool.suport;

import java.util.HashMap;
import java.util.Map;

 public class SettingResource {
	private  Map<String, String> settings;
	public SettingResource() 
	{
		Map<String, String> settings = new HashMap<>();
		settings.put("connection.driver_class", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
		settings.put("dialect", "org.hibernate.dialect.SQLServer2012Dialect");
		settings.put("hibernate.connection.url", "jdbc:sqlserver://localhost:1433;databaseName=PracticeDB");
		settings.put("tomee.jpa.cdi", "false");
		settings.put("transaction.factory_class", "org.hibernate.transaction.JTATransactionFactory");
		settings.put("hibernate.connection.username", "sa");
		settings.put("hibernate.connection.password", "six983son410");
		settings.put("hibernate.hbm2ddl.auto", "update");
		settings.put("hibernate.show_sql", "true");
		settings.put("hibernate.format_sql", "true");
		this.settings=settings;
	}
	public Map<String, String> getSettings()
	{
		return settings;
	}
}
