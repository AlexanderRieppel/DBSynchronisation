package Controller;

import java.sql.SQLException;

import DBConection.MYSQLConection;
import DBConection.POSTConection;

public class Controller {
	public static void main(String[] args){
		try {
			new MYSQLConection();
			new POSTConection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
