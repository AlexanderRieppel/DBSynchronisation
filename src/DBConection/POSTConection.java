package DBConection;


import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.postgresql.PGNotification;


public class POSTConection {
	private String jdriv = "org.postgresql.Driver", url = "jdbc:postgresql://10.0.0.16/tgmbank", uname = "postgres", pwd ="HalliGalli15", dquary = "SELECT * FROM konto";
	
	private Connection con;
	private Statement st;
	private ResultSet rs;
	private ResultSetMetaData rsmd;
	
	private boolean connected = false;
	
	public POSTConection() throws ClassNotFoundException, SQLException{
		System.out.println("Postgres Treiber wird geladen......");
		
		Class.forName(jdriv);
		
		System.out.println("Postgres wird verbunden......");
		con = DriverManager.getConnection(url , uname, pwd);
		
		st = con.createStatement( 
		         ResultSet.TYPE_SCROLL_INSENSITIVE,
		         ResultSet.CONCUR_READ_ONLY );
		
		connected = true;
		
		System.out.println("Postgres conected!");
		
	}
}
