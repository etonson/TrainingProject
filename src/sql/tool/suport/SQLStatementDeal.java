package sql.tool.suport;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLStatementDeal {

	public String[] sqlQuery2Array(String SqlStatempent) {
		String[] tmpStrArray = SqlStatempent.split(";");
		String[] ddlData = new String[tmpStrArray.length];
		for (int i = 0; i < tmpStrArray.length; i++) {
			ddlData[i] = tmpStrArray[i].trim();
		}
		return ddlData;
	}

	public boolean isCreatedClass(String sqlQuery) {
		sqlQuery = sqlQuery.toLowerCase();
		Pattern pattern = Pattern.compile("create table.*");
		Matcher matcher = pattern.matcher(sqlQuery);
		return matcher.matches();
	}

	public boolean isAddReferencesdColumnClass(String sqlQuery) {
		sqlQuery = sqlQuery.toLowerCase();
		Pattern pattern = Pattern.compile("alter table .* add constraint .* foreign key .* references .*");
		Matcher matcher = pattern.matcher(sqlQuery);
		return matcher.matches();
	}
	public boolean isAddUpdatedColumn(String sqlQuery) {
		sqlQuery = sqlQuery.toLowerCase();
		Pattern pattern = Pattern.compile("alter table.* add .*");
		Matcher matcher = pattern.matcher(sqlQuery);
		return matcher.matches();
	}

	public String removeSpecialChar(String sqlQuery) {
		Pattern pattern = Pattern.compile("\t|\r|\n");
		Matcher matcher = pattern.matcher(sqlQuery);
		sqlQuery = matcher.replaceAll("");
		return sqlQuery;
	}

	public String removeCons(String sqlQuery) {
		Pattern pattern = Pattern.compile("not null");
		Matcher matcher = pattern.matcher(sqlQuery);
		sqlQuery = matcher.replaceAll("");

		pattern = Pattern.compile("       ");
		matcher = pattern.matcher(sqlQuery);
		sqlQuery = matcher.replaceAll("");

		pattern = Pattern.compile(".*primary key (.*).*");
		matcher = pattern.matcher(sqlQuery);

		if (matcher.matches()) {
			pattern = Pattern.compile("primary key (.*)");
			matcher = pattern.matcher(sqlQuery);
			sqlQuery = matcher.replaceAll("");
			sqlQuery = sqlQuery.trim();
			StringBuilder sb = new StringBuilder(sqlQuery.length());
			sb.append(sqlQuery);
			sb.delete(sqlQuery.length() - 1, sqlQuery.length());
			sb.append(")");
			sqlQuery = sb.toString();
		}
		return sqlQuery+";";
	}
}
