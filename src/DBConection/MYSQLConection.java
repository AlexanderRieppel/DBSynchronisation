package DBConection;


import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.JdbcRowSet;



public class MYSQLConection implements Runnable{
	private String jdriv = "com.mysql.jdbc.Driver", url, uname , pwd, dbname;
	private ArrayList<String> tab = new ArrayList<String>();
	
	private Connection con;
	private Statement st;
	private ResultSet rs;
	
	private boolean connected = false;
	
	public MYSQLConection(String url, String dbname, String uname, String pwd) {
		this.url = "jdbc:mysql://" + url + "/" + dbname;
		this.uname = uname;
		this.pwd = pwd;
		this.dbname = dbname;
	}
	public void connect()throws ClassNotFoundException, SQLException{
		System.out.println("Mysql Treiber wird geladen......");
		
		Class.forName(this.jdriv);
		
		System.out.println("Mysql wird verbunden......");
		con = DriverManager.getConnection(this.url , this.uname, this.pwd);
		
		st = con.createStatement( 
		         ResultSet.TYPE_SCROLL_SENSITIVE,
		         ResultSet.CONCUR_UPDATABLE);
		
		this.connected = true;
		
		System.out.println("Mysql conected!");
		System.out.println("Es wird Datenbank " + dbname + " verwendet!");
	}
	public ArrayList<String> getTables() throws SQLException{
		rs = st.executeQuery("show tables");
		while(rs.next()){
			tab.add(rs.getString(1));
		}
		return tab;
	}
	public ResultSetMetaData getMeta(String tabn) throws SQLException{
		return st.executeQuery("SELECT * FROM " + tabn).getMetaData(); 
	}

	@Override
	public void run() {
		System.out.println("Mysql Check gestartet!");
		while(!Thread.interrupted()){
			ResultSet rs1;
			try {
				//rs1 = st.executeQuery(dquary);
				
					//System.out.println("Mysql eine neue änderung!");
					//Thread.sleep(9999999);
				
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.err.println("ERROR 02: " + e.getMessage());
			}
		}
		
	}
}
