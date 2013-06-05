package Controller;

import java.sql.SQLException;

import DBConection.MYSQLConection;
import DBConection.POSTConection;

public class Controller {
	public static void main(String[] args){
		try {
			MYSQLConection m = new MYSQLConection();
			new POSTConection();
			Thread t = new Thread(m);
			t.start();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
