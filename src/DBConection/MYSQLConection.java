package DBConection;


import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.JdbcRowSet;


import Listener.Listener;

import com.mysql.jdbc.Driver;
import com.sun.rowset.JdbcRowSetImpl;


public class MYSQLConection implements Runnable{
	private String jdriv = "com.mysql.jdbc.Driver", url = "jdbc:mysql://127.0.0.1/tgmbank", uname = "root", pwd ="HalliGalli15";
	private String dquary = "show tables";
	private ArrayList<String> tab = new ArrayList<String>();
	
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
		this.getTables();
		for(int i = 0; i < tab.size();i++){
			System.out.println(tab.get(i));
		}
	}
	public void getTables() throws SQLException{
		do{
			tab.add(rs.getString(1));
		}while(rs.next());
	}

	@Override
	public void run() {
		System.out.println("Mysql Check gestartet!");
		while(!Thread.interrupted()){
			ResultSet rs1;
			try {
				rs1 = st.executeQuery(dquary);
				
					System.out.println("Mysql eine neue änderung!");
					//Thread.sleep(9999999);
				
				Thread.sleep(100);
			} catch (SQLException e) {
				System.err.println("ERROR 01: " + e.getMessage());
			} catch (InterruptedException e) {
				System.err.println("ERROR 02: " + e.getMessage());
			}
		}
		
	}
}
