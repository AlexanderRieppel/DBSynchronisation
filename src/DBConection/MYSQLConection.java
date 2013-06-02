package DBConection;


import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


public class MYSQLConection {
	private String jdriv = "com.mysql.jdbc.Driver", url = "jdbc:mysql://10.0.0.16/tgmbank", uname = "root", pwd ="HalliGalli15", dquary = "SELECT * FROM konto";
	
	private Connection con;
	private Statement st;
	private ResultSet rs;
	private ResultSetMetaData rsmd;
	
	private boolean connected = false;
	
	public MYSQLConection() throws ClassNotFoundException, SQLException{
		System.out.println("Mysql Treiber wird geladen......");
		
		Class.forName(jdriv);
		
		System.out.println("Mysql wird verbunden......");
		con = DriverManager.getConnection(url , uname, pwd);
		
		st = con.createStatement( 
		         ResultSet.TYPE_SCROLL_INSENSITIVE,
		         ResultSet.CONCUR_READ_ONLY );
		
		connected = true;
		
		System.out.println("Mysql conected!");
	}
}
