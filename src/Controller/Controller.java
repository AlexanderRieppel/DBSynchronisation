package Controller;

import java.sql.SQLException;

import DBConection.MYSQLConection;

public class Controller {
	public static void main(String[] args){
		try {
			new MYSQLConection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
