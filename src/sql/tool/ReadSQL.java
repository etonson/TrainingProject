package sql.tool;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import sql.tool.suport.SQLStatementDeal;

public class ReadSQL {

	public static void main(String[] args) {
		try {
			String sqlList = loadSql("D:\\pwd\\updateExport.sql");
			SQLStatementDeal stool = new SQLStatementDeal(sqlList);
			String[] strArray = stool.sqlQuery2Array();
			for(int i =0 ; i<strArray.length;i++) 
			{
				String str = stool.removeCons(strArray[i]);
				System.out.println(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 讀取 SQL 檔案,獲取 SQL 語句
	 * 
	 * @param sqlFile SQL 指令碼檔案
	 * @return List<sql> 返回所有 SQL 語句的 List
	 * @throws Exception
	 */
	private static String loadSql(String sqlFile) throws Exception {
		List<String> sqlList = new ArrayList<String>();
		try (InputStream sqlFileIn = new FileInputStream(sqlFile);) {
			StringBuffer sqlSb = new StringBuffer();
			byte[] buff = new byte[1024];
			int byteRead = 0;
			while ((byteRead = sqlFileIn.read(buff)) != -1) {
				sqlSb.append(new String(buff, 0, byteRead));
			}
			// Windows 下換行是 //r//n, Linux 下是 //n
			String[] sqlArr = sqlSb.toString().split("(;////s*////r////n)|(;////s*////n)");
			String sql = null;
			for (int i = 0; i < sqlArr.length; i++) {
				sql = sqlArr[i].replaceAll("--.*", "").trim();
			}
			return sql;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * 傳入連線來執行 SQL 指令碼檔案,這樣可與其外的資料庫操作同處一個事物中
	 * 
	 * @param conn    傳入資料庫連線
	 * @param sqlFile SQL 指令碼檔案 可選引數,為空字串或為null時 預設路徑為
	 *                src/test/resources/config/script.sql
	 * @throws Exception
	 */
//    public static void execute(Connection conn,String sqlFile) throws Exception {
//        Statement stmt = null;
//        if(sqlFile==null||"".equals(sqlFile)){
//            sqlFile="src/test/resources/config/script.sql";
//        }
//        List<String> sqlList = loadSql(sqlFile);
//        stmt = conn.createStatement();
//        for (String sql : sqlList) {
//            stmt.addBatch(sql);
//        }
//        int[] rows = stmt.executeBatch();
//        System.out.println("Row count:" + Arrays.toString(rows));
//    }

}