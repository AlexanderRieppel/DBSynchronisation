package Controller;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import DBConection.Conection;
import DBConection.MYSQLConection;
import DBConection.POSTConection;

public class Controller {
	private Conection m, p;
	public static void main(String[] args){
		new Controller();
	}
	public Controller(){
		try {
			m = new MYSQLConection("10.0.0.16", "tgmbank","root", "HalliGalli15",this);
			m.connect();
			p = new POSTConection("10.0.0.16", "tgmbank","postgres", "HalliGalli15",this);
			p.connect();
			//p.getTables();
			this.compareTabels();
			m.initTC();
			p.initTC();
			Thread mt = new Thread(m);
			Thread pt = new Thread(p);
			mt.start();
			pt.start();
			
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR01:" + e.getMessage());
		} catch (SQLException e) {
			System.err.println("ERROR02:" + e.getMessage());
			//e.printStackTrace();
		}
	}
	public void setMYSQLC(int id, int wert){
		m.setC(id, wert);
	}
	public void setPOSTC(int id, int wert){
		p.setC(id, wert);
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
			System.out.println("Tabellen check bestanden Programm wird weiter ausgeführt!");
			for(int i = 0 ; i < mtab.size();i++){
				this.match(true,true, mtab.get(i));
				this.match(true,false, mtab.get(i));
				this.match(true,true, mtab.get(i));
				this.match(true,false, mtab.get(i));
			}
		}
	}
	
	public void match(boolean mainmysql, boolean insert, String tabn) throws SQLException{
		ResultSet mrs = m.exeQuarry("SELECT * FROM " + tabn);
		ResultSet prs = p.exeQuarry("SELECT * FROM " + tabn);
		ResultSetMetaData mrsm = mrs.getMetaData();
		ResultSetMetaData prsm = prs.getMetaData();
		DatabaseMetaData meta = m.getMe();
		ResultSet prim = meta.getPrimaryKeys(null, null, tabn);
		prim.next();
		String pmr = prim.getString(4);
		int primid = 0;
		for(int i = 1; i <= mrsm.getColumnCount();i++){
			if(mrsm.getColumnName(i).equalsIgnoreCase(pmr)){
				primid = i;
			}
		}
		mrs = m.exeQuarry("SELECT * FROM " + tabn + " ORDER BY " + mrsm.getColumnName(primid));
		prs = p.exeQuarry("SELECT * FROM " + tabn + " ORDER BY " + prsm.getColumnName(primid));
		if(primid > 0){
			if(insert == true){
				if(mainmysql == true){
					int durch = 0;
					while(mrs.next()){
						prs = p.exeQuarry("SELECT * FROM " + tabn + " ORDER BY " + prsm.getColumnName(primid));
						for(int id = 0; id < durch;id++)
							prs.next();
						if(!prs.next()){
							String qu = "INSERT INTO " + tabn + " VALUES (";
							for(int i = 1; i <= prsm.getColumnCount(); i ++){
								if(prsm.getColumnType(i) == java.sql.Types.INTEGER){
									qu+=mrs.getInt(i) ;
								}else if( prsm.getColumnType(i) == java.sql.Types.DOUBLE || prsm.getColumnType(i) == java.sql.Types.FLOAT){
									qu += mrs.getDouble(i) ;
								}else{
									qu += "'" + mrs.getString(i) + "'";
								}
								
								if(!(i == prsm.getColumnCount() ))
									qu += ",";
							}
							qu+=")";
							//System.out.println(qu);
							p.exeUpdate(qu);
						}
						durch ++;
					}
				}else{
					int durch = 0;
					while(prs.next()){
						mrs = m.exeQuarry("SELECT * FROM " + tabn + " ORDER BY " + mrsm.getColumnName(primid));
						for(int id = 0; id < durch;id++)
							mrs.next();
						if(!mrs.next()){
							String qu = "INSERT INTO " + tabn + " VALUES (";
							for(int i = 1; i <= mrsm.getColumnCount(); i ++){
								if(mrsm.getColumnType(i) == java.sql.Types.INTEGER){
									qu+=prs.getInt(i) ;
								}else if( mrsm.getColumnType(i) == java.sql.Types.DOUBLE || mrsm.getColumnType(i) == java.sql.Types.FLOAT){
									qu += prs.getDouble(i) ;
								}else{
									qu += "'" + prs.getString(i) + "'";
								}
								
								if(!(i == mrsm.getColumnCount() ))
									qu += ",";
							}
							qu+=")";
							m.exeUpdate(qu);
							
						}
						durch ++;
						
					}
				}
			}else if(insert == false){
				if(mainmysql == true){
					int durch = 0;
					boolean mb = true , pb = true;
					while(mb || pb){
						mb = mrs.next();
						prs = p.exeQuarry("SELECT * FROM " + tabn + " ORDER BY " + prsm.getColumnName(primid));
						for(int id = 0; id <= durch;id++)
							pb = prs.next();
						if(pb == true && mb == true){
							if(mrs.getInt(primid) != prs.getInt(primid)){
								//System.out.println(prsm.getColumnName(primid));
								p.exeUpdate("DELETE FROM " + tabn + " WHERE " + prsm.getColumnName(primid) + " = " + prs.getInt(primid));
							}
							
						}else if(mb == false && pb==true){
							p.exeUpdate("DELETE FROM " + tabn + " WHERE " + prsm.getColumnName(primid) + " = " + prs.getInt(primid));
						}
						durch ++;
					}
				}else if(mainmysql == false){
					int durch = 0;
					boolean mb = true , pb = true;
					while(mb || pb){
						pb = prs.next();
						mrs = m.exeQuarry("SELECT * FROM " + tabn + " ORDER BY " + prsm.getColumnName(primid));
						for(int id = 0; id <= durch;id++)
							mb = mrs.next();
						if(pb == true && mb == true){
							if(mrs.getInt(primid) != prs.getInt(primid)){
								//System.out.println(prsm.getColumnName(primid));
								m.exeUpdate("DELETE FROM " + tabn + " WHERE " + prsm.getColumnName(primid) + " = " + prs.getInt(primid));
							}
							
						}else if(pb == false && mb == true){
							m.exeUpdate("DELETE FROM " + tabn + " WHERE " + prsm.getColumnName(primid) + " = " + prs.getInt(primid));
						}
						durch ++;
					}
				}
			}
		}else{
			System.err.println("ERROR: Primarykey nicht gefunden!\nProgramm wird beendet!");
			System.exit(0);
		}		
	}
}
