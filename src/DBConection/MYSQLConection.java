package DBConection;


import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.sql.rowset.JdbcRowSet;


import Listener.Listener;

import com.mysql.jdbc.Driver;
import com.sun.rowset.JdbcRowSetImpl;


public class MYSQLConection{
	private String jdriv = "com.mysql.jdbc.Driver", url = "jdbc:mysql://10.0.0.16/tgmbank", uname = "root", pwd ="HalliGalli15", dquary = "SELECT * FROM konto";
	
	private Connection con;
	private Statement st;
	private ResultSet rs;
	private ResultSetMetaData rsmd;
	private JdbcRowSet rows;
	
	private boolean connected = false;
	
	public MYSQLConection() throws ClassNotFoundException, SQLException{
		System.out.println("Mysql Treiber wird geladen......");
		
		Class.forName(jdriv);
		
		System.out.println("Mysql wird verbunden......");
		con = DriverManager.getConnection(url , uname, pwd);
		
		st = con.createStatement( 
		         ResultSet.TYPE_SCROLL_SENSITIVE,
		         ResultSet.CONCUR_UPDATABLE);
		
		connected = true;
		
		System.out.println("Mysql conected!");
		rs = st.executeQuery(dquary);
		rsmd = rs.getMetaData();
		rows = new JdbcRowSetImpl(rs);
		rows.addRowSetListener(new Listener());
			
	}
}
