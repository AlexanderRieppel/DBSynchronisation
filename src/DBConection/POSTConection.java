package DBConection;


import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import org.postgresql.PGNotification;


public class POSTConection implements Runnable{
	private String jdriv = "org.postgresql.Driver", url, uname , pwd, dbname;
	private ArrayList<String> tab = new ArrayList<String>();
	
	private Connection con;
	private Statement st;
	private ResultSet rs;
	private ResultSetMetaData rsmd;
	
	private boolean connected = false;
	
	public POSTConection(String url, String dbname, String uname, String pwd) throws ClassNotFoundException, SQLException{
		this.url = "jdbc:postgresql://" + url + "/" + dbname;
		this.uname = uname;
		this.pwd = pwd;
		this.dbname = dbname;
	}
	public void connect()throws ClassNotFoundException, SQLException{
		System.out.println("Postgres Treiber wird geladen......");
		
		Class.forName(jdriv);
		
		System.out.println("Postgres wird verbunden......");
		con = DriverManager.getConnection(url , uname, pwd);
		
		st = con.createStatement( 
		         ResultSet.TYPE_SCROLL_INSENSITIVE,
		         ResultSet.CONCUR_READ_ONLY );
		
		connected = true;
		
		System.out.println("Postgres conected!");
		System.out.println("Es wird Datenbank " + dbname + " verwendet!");
	}
	public ArrayList<String> getTables() throws SQLException{
		rs = st.executeQuery("\\d");
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
		
		
	}
}
