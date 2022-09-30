package sql.tool.suport;

public class SQLStatementDeal {
	private String SqlStatempent;

	public SQLStatementDeal(String SqlStatempent) {
		this.SqlStatempent = SqlStatempent;
	}

	public String[] sqlQuery2Array() {
		String[] tmpStrArray = SqlStatempent.split(";");
		String[] ddlData = new String[tmpStrArray.length];
		for(int i=0;i<tmpStrArray.length;i++) 
		{
			ddlData[i] = tmpStrArray[i].trim();
			
		}
		return ddlData;
	}

	public String removeCons(String tmpStr) {
		String[] tmpStrArray = tmpStr.split(",");
		String sqlQuery = "";
		tmpStr.replace ("not null","");
		for(int i =0;i<tmpStrArray.length;i++) 
		{
			if(tmpStrArray[i].contentEquals("primary key")) 
			{
				tmpStrArray[i]="";
			}
			sqlQuery+=(","+tmpStrArray[i]);
		}
		return sqlQuery;
	}
	
	
	
	public void setSqlQuery2Array(String SqlStatempent) {
		this.SqlStatempent = SqlStatempent;
	}

	public String getSqlQuery2Array() {
		return this.SqlStatempent;
	}

}
