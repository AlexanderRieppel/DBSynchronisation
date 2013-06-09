package DBConection;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public interface Conection extends Runnable{
	public void connect()throws ClassNotFoundException, SQLException;
	public ArrayList<String> getTables() throws SQLException;
	public ResultSetMetaData getMeta(String tabn) throws SQLException;
	public ResultSet exeQuarry(String q)throws SQLException;
	public int exeUpdate(String q)throws SQLException;
	public void initTC()throws SQLException;
	public DatabaseMetaData getMe() throws SQLException;
	public void setC(int id, int wert);
}
