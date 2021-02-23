package Server;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import Server.Table_Schema.Column;
/**
 *gestione dei dati contenuti in una
 *tabella.
 *
 */
public class Table_Data
{
	/**Nested class di Table_Data;
	 *modella una tupla della tabella 
	 */
	public class TupleData
	{
		/**
		 * tupla della tabella
		 */
		public List<Object> tuple=new ArrayList<Object>();

		/**
		 * Stampa una tupla della tabella
		 */
		public String toString()
		{
			String value="";
			Iterator<Object> it= tuple.iterator();
			while(it.hasNext())
				value+= (it.next().toString() +" ");
			return value;
		}
	}

	/**
	 * Interroga la tabella passata in input. Dopo aver effettuato la selezione di tutte
	 *le tuple della tabella, )per ogni tupla controlla se un elemento è String oppure Float e lo
	 *aggiunge alla tupla corrente. Infine aggiunge la tupla alla lista di tuple da restituire in uscita.
	 *NOTA: ogni tupla contente valori nulli, non viene presa in considerazione e non viene aggiunta alla lista in output.
	 * @param table tabella da interrogare
	 * @return Lista di tuple contenute nella tabella interrogata
	 * @throws SQLException errori nella interazione con il DB
	 */
	public List<TupleData> getTransazioni(String table) throws SQLException
	{
		List<TupleData> trans = new LinkedList<TupleData>();
		Statement statement;
		//avvaloro conn per la interazione con il DB
		Connection conn = DbAccess.getConnection();
		statement = conn.createStatement();
		ResultSet rs = statement.executeQuery("SELECT * FROM " + table);
		int columns = rs.getMetaData().getColumnCount(); //prendo il numero delle colonne
		while(rs.next())//ad ogni ciclo passa alla tupla(riga) successiva
		{
			int accettaTupla = 0;
			TupleData tupla = new TupleData();
			for(int i = 1; i <= columns; i++)
			{
				//se troviamo un valore nullo, la tupla non viene conosiderata poichè non consistente per il calcolo dei pattern
				if(rs.getObject(i) == null)
				{
					System.out.println("La tupla " + rs + " contiene uno o più valori nulli, dunque non viene presa in considerazione");
					if(trans.contains(tupla)) //evitiamo la tentata rimozione di una tupla non ancora inserita, che porterebbe a una eccezione a runtime
					{
						trans.remove(tupla);
						break;
					}
					else
						break;
				}
				else
					accettaTupla++;
				tupla.tuple.add(rs.getObject(i));
			}
			if(accettaTupla == columns)// condizione necessaria per non aggiungere tuple con valori nulli
				trans.add(tupla); 
		}
		//chiudo lo statement
		statement.close();
		//chiudo il resultSet
		rs.close();
		return trans;	
	}
	/**
	 * Interroga la tabella passata in input. Seleziona dalla tabella solo la colonna
	 *passata in input restituendo i valori distinti dell'attributo column
	 * @param table tabella da interrogare
	 * @param column colonna di cui restituire gli attributi
	 * @return lista popolata dagli attributi presenti nella colonna specificata
	 * @throws SQLException Problemi con mysql.
	 */
	public List<Object>getColumnValues(String table, Column column) throws SQLException
	{
		//creo la lista che ospiterà gli attributi della colonna
		List<Object> colonna = new LinkedList<Object>();
		//avvaloro conn per la interazione con il DB
		Connection conn = DbAccess.getConnection();
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery("SELECT " + column.getColumnName() + " FROM " + table);
		while (rs.next())
			colonna.add(rs.getObject(1)); //1 poichè il result set sarà di un solo elemento, e prendo ovviamente solo il primo
		statement.close();
		rs.close();
		return colonna;
	}
	/**
	 * Interroga la tabella passata in input. Seleziona dalla tabella solo la colonna
	 *passata in input restituendo i valori aggregati dell'attributo column rispetto all'argomento
	 * @param table Tabella da interrogare,
	 * @param column colonna da interrogare
	 * @param aggregate aggregato SQL da ottenere (min,max)
	 * @return Lista dei valori di un attributo.
	 * @throws SQLException errore nella comunicazione con il DB
	 * @throws NoValueException nessun valore presente
	 */
	public Object getAggregateColumnValue(String table,Column column,QUERY_TYPE aggregate)	throws SQLException,NoValueException
	{
		//nel nostro caso l'operatore aggregato sarà MAX oppure MIN, quindi la query restiruirà
		// il massimo oppure il minimo della colonna
		Connection conn = DbAccess.getConnection();
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery("SELECT " + aggregate + "(" + column.getColumnName() + ") " + " FROM " + table);
		rs.next(); //se non ci si posiziona nella prima posizione si avrà una eccezione
		Object out = rs.getObject(1);
		if(out == null)  //caso estremo di una tabella tutta di null
			throw new NoValueException();
		rs.close();
		statement.close();
		return out;
	}
}
