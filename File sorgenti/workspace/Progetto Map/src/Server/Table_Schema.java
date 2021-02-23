package Server;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
/**
 * modella una tabella di un database relazionale
 *
 */
public class Table_Schema
{
	List<Column> tableSchema = new ArrayList<Column>();
	/**
	 * Gestisce la creazione della tabella tableName
	 * @param tableName nome della tabella da prendere dal db per la creazione di questo oggetto
	 * @throws SQLException Problemi con mysql.
	 */
	//Necessario rendere la classe void poichè non restituisce nulla
	public Table_Schema(String tableName) throws SQLException{
		HashMap<String,String> mapSQL_JAVATypes=new HashMap<String, String>();
		mapSQL_JAVATypes.put("CHAR","string");
		mapSQL_JAVATypes.put("VARCHAR","string");
		mapSQL_JAVATypes.put("LONGVARCHAR","string");
		mapSQL_JAVATypes.put("BIT","string");
		mapSQL_JAVATypes.put("SHORT","number");
		mapSQL_JAVATypes.put("INT","number");
		mapSQL_JAVATypes.put("LONG","number");
		mapSQL_JAVATypes.put("FLOAT","number");
		mapSQL_JAVATypes.put("DOUBLE","number");
		Connection con=DbAccess.getConnection();
		DatabaseMetaData meta = con.getMetaData();
		ResultSet res = meta.getColumns(null, null, tableName, null);
		while (res.next()) {
			if(mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
				tableSchema.add(new Column( res.getString("COLUMN_NAME"),
						mapSQL_JAVATypes.get(res.getString("TYPE_NAME"))));
		}
		res.close();
	}
	
	/**
	 *Gestisce le colonne della nostra tabella
	 */
	public class Column{
		/**Nome della colonna */
		private String name;
		
		/**Tipo dell'attributo espresso dal nome della colonna*/
		private String type;
		
		/**
		 * Avvalora il nome della colonna e il suo tipo con i parametri in input.
		 * @param name Nome della colonna 
		 * @param type Tipo dell'attributo espresso dal nome della colonna
		 */
		Column(String name,String type){
			this.name=name;
			this.type=type;
		}
		/**
		 * Accede all'oggetto colonna per conoscerne il nome
		 * @return nome della colonna
		 */
		public String getColumnName(){
			return name;
		}
		/**
		 * Controlla se la colonna sia un attributo di tipo numerico, e quindi un attributo continuo.
		 * @return true se il tipo è numerico, false se il tipo è una stringa e quindi l'attributo sarà discreto.
		 */
		public boolean isNumber(){
			return type.equals("number");
		}
		public String toString(){
			return name+":"+type;
		}
	}
	/**
	 * Restituisce il numero dei campi della tabella.
	 * @return numero degli attributi (e quindi delle colonne della tabella).
	 * @throws SQLException Problemi con mysql.
	 */
	public int getNumberOfAttributes() throws SQLException
	{
		return this.tableSchema.size();
	}
	/**
	 * Ricerca una colonna sulla base di un indice passato in input
	 * @param index Indice passato in input per effettuare la ricerca di una colonna, che parte da 1 e non da 0
	 * @return Colonna in posizione index
	 * @throws SQLException Problemi con mysql.
	 */
	public Column getColumn(int index) throws SQLException
	{

		return this.tableSchema.get(index);
	}
}