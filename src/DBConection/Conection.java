package DBConection;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * Dies Interface gibt die Conection für die Datenbanken vor
 * @author Dominik Backhausen
 * @version 0.1
 */
public interface Conection extends Runnable{
	/**
	 * Baut Verbindung zu Datenbank auf
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void connect()throws ClassNotFoundException, SQLException;
	/**
	 * Gibt die Tabellen Namen zurück die sich in Der Datenbank befinden
	 * @return Liste von TabellenNamen
	 * @throws SQLException
	 */
	public ArrayList<String> getTables() throws SQLException;
	/**
	 * Gibt die MetaDaten von einer bestimmten Tabelle zurück
	 * @param tabn Tabellenname
	 * @return Metadaten der Tabelle
	 * @throws SQLException
	 */
	public ResultSetMetaData getMeta(String tabn) throws SQLException;
	/**
	 * Fürrt eine Quarry aus und gibt ein Resultset zurück
	 * @param q die auszuführende Quarry
	 * @return das Ergebniss der Quarry
	 * @throws SQLException
	 */
	public ResultSet exeQuarry(String q)throws SQLException;
	/**
	 * Führt ein Update aus und gibt die anzahl der änderungen zurück
	 * @param q Updatebefehl
	 * @return anzahl der Änderungen
	 * @throws SQLException
	 */
	public int exeUpdate(String q)throws SQLException;
	/**
	 * Initialisiert den Zahler für die Tabeleneinträge
	 * @throws SQLException
	 */
	public void initTC()throws SQLException;
	/**
	 * Gibt die Metadaten der Datenbank zurück
	 * @return Metadaten der Datenbank
	 * @throws SQLException
	 */
	public DatabaseMetaData getMe() throws SQLException;
	/**
	 * Setzt die tabellenzeilen Zähler für eine bestimmte Tabelle
	 * @param id index der Tabelle
	 * @param wert neuer wert
	 */
	public void setC(int id, int wert);
}
