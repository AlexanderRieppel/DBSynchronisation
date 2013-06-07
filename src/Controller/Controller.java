package Controller;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import DBConection.MYSQLConection;
import DBConection.POSTConection;

public class Controller {
	private MYSQLConection m;
	private POSTConection p;
	public static void main(String[] args){
		new Controller();
	}
	public Controller(){
		try {
			m = new MYSQLConection("127.0.0.1", "tgmbank","root", "HalliGalli15");
			m.connect();
			p = new POSTConection("127.0.0.1", "tgmbank","postgres", "HalliGalli15");
			p.connect();
			this.compareTabels();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void compareTabels() throws SQLException{
		ArrayList<String> mtab = m.getTables();
		ArrayList<String> ptab = p.getTables();
		Collections.sort(mtab);
		Collections.sort(ptab);
		if(mtab.size() == ptab.size()){
			for(int i  = 0; i < mtab.size();i++){
				if(!mtab.get(i).equalsIgnoreCase(ptab.get(i))){
					System.out.println("Tabellen passen nicht zusammen!\nProgramm wird beendet");
					System.exit(0);
				}else{
					ResultSetMetaData mrmd = m.getMeta(mtab.get(i));
					ResultSetMetaData prmd = p.getMeta(ptab.get(i));
					if(mrmd.getColumnCount() == prmd.getColumnCount()){
						for(int ii = 1; i < mrmd.getColumnCount();i++){
							if(!((mrmd.getColumnName(ii).equalsIgnoreCase(prmd.getColumnName(ii)))&&
									(mrmd.getColumnType(ii) == prmd.getColumnType(ii)))){
								System.out.println("Tabellen informationen stimmen nicht überin!\nProgramm wird beendet!");
								System.exit(0);
							}
						}
					}
				}
			}
		}
	}
}
