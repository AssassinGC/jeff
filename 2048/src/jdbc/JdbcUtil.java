package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtil {
	    private static String dbUrl="jdbc:mysql://localhost:3306/my2048?userUnicode=true&characterEncoding=utf-8";
	    private static String dbUser="root";
		private static String dbPassword="shuang";//密码
		private static String jdbcName="com.mysql.jdbc.Driver";

		//连接数据库
		public static Connection getConn(){
			Connection conn=null;
			try{
				Class.forName(jdbcName);
			}
			catch(Exception e){}
			try{
				conn=DriverManager.getConnection(dbUrl,dbUser,dbPassword);
			}
			catch(SQLException ex){}
			return conn;		
		}
		public static void release(Connection conn,Statement st,ResultSet rs) throws SQLException{
			if(conn != null){
				conn.close();
			}
			if( st!= null){
				st.close();
			}
			if(rs != null){
				rs.close();
			}
		}
}
