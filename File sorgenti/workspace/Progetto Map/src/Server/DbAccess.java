package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *@author Stigliano-Masella
 * @version 1.1
 * <p>Company:Dipartimento di Informatica,Universita degli studi di Bari </p>
 * <p>Class Description: Gestisce l'accesso alla base di dati . </p>
 *<p> Copyright:Copyright (c) 2017 </p>
 *
 */
public class DbAccess
{
	static String DRIVER_CLASS_NAME = "org.gjt.mm.mysql.Driver";
	private static final String DBMS = "jdbc:mysql";
	private static final String SERVER="localhost";
	//private static final String DATABASE = "CFP17_matricola"; COMMENTATA POICHE' E' STATA AGGIUNTA UN ESTENSIONE CHE PERMETTE DI SCEGLIERE UN NUOVO DB.
	private static final String PORT="3306";
	private static final String USER_ID = "stigliano_642472_17";
	private static final String PASSWORD = "CFP17password";
	private static Connection conn;
/**
 * Provvede a caricare il driver per stabilire la connessione con la base di dati e inizializza tale conessione.
 * @param db database da scegliere
 */
	public static void initConnection(String db)
	{
		
		String connectionString=DBMS+"://"+SERVER+":"+PORT+"/"+ db;
		try
		{
			Class.forName(DRIVER_CLASS_NAME).newInstance();
		}
		catch(Exception ex)
		{
			System.err.println("Impossibile trovare il Driver"+DRIVER_CLASS_NAME);
		}
		try
		{
			conn=DriverManager.getConnection(connectionString,USER_ID,PASSWORD);
		}
		catch(SQLException e)
		{
			System.err.println("Impossibile connettersi al DB");
			e.printStackTrace();
		}
	}
	/**
	 * Restituisce la connessione corrente.
	 * @return conn connessione corrente.
	 */
	public static Connection getConnection()
	{
		return conn;
	}
	/**
	 * Chiude la connessione con il database.
	 * @throws SQLException Problemi con mysql.
	 */
	public static void closeConnection() throws SQLException 
	{
	 conn.close();
	}
	
}

