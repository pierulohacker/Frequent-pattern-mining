package Server;



import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import Server.Table_Data.TupleData;
import Server.Table_Schema.Column;

/**
 * 
 * @author Masella-Stigliano
 * @version 2.0
 * <p>Company:Dipartimento di Informatica,Università degli studi di Bari </p>
 * <p>Class Description: comunica con il server e immagazzina i dati necessari al calcolo dei pattern </p>
 *<p> Copyright:Copyright (c) 2017 </p>
 */
public class Data {

	/**contiene l'insieme di transazioni che riceve dal db */
	private Object data[][];
	
	/**cardinalità dell'insieme delle transazioni*/
	private int numberOfExamples;
	
	/**Una lista di attributi*/
	private List<attribute> attributeSet=new LinkedList<attribute>();

	//COSTRUTTORE
	/**
	 * Costruttore che avvalora la matrice "data [][]" con gli elementi presi dalla tabella del db entrambi specificati in input, e calcola il numero di tuple
	 * avvalorando numberOfExamples
	 * @param table tabella del Database
	 * @param db Database su cui lavorare
	 * @throws DatabaseConnectionException problemi di comunicazione col server
	 * @throws NoValueException valore non presente
	 * @throws SQLException problemi in mysql
	 */
	public Data(String table, String db) throws DatabaseConnectionException, NoValueException, SQLException {

		//		Object data[][] = {{"Sunny", "Hot", "High", "Weak", "No"},
		//				{"Sunny", "Hot", "High", "Strong", "No"},
		//				{"Overcast", "Hot", "High", "Weak", "Yes"},
		//				{"Rain", "Mild", "High", "Weak", "Yes"},
		//				{"Rain", "Cool", "Normal", "Weak", "Yes"},
		//				{"Rain", "Cool", "Normal", "Strong", "No"},
		//				{"Overcast", "Cool", "Normal", "Strong", "Yes"},
		//				{"Sunny", "Mild", "High", "Weak", "No"},
		//				{"Sunny", "Cool", "Normal", "Weak", "Yes"},
		//				{"Rain", "Mild", "Normal", "Weak", "Yes"},
		//				{"Sunny", "Mild", "Normal", "Strong", "Yes"},
		//				{"Overcast", "Mild", "High", "Strong", "Yes"},
		//				{"Overcast", "Hot", "Normal", "Weak", "Yes"},
		//				{"Rain", "Mild", "High", "Strong", "No"}};
		//		this.data = data;
		//		

		/*System.out.println(Arrays.deepToString(this.data));//stampo tutta la matrice
		System.out.println(this.data.length);//ottengo banalmente il numero delle transazioni
		 */

		//Avvaloramento dell'attributo temperature come chiesto da traccia 5
		//è possibile usare valori float grazie alla classe contious attribute, sottoclasse di attribute

		DbAccess.initConnection(db); //connessione al database specificato in input
		Table_Schema tab = new Table_Schema(table);
		int attributesNumber = tab.getNumberOfAttributes();
		for(int i = 0; i < attributesNumber; i++)
		{
			Table_Data query = new Table_Data();
			Column colonna = tab.getColumn(i);
			//caso in cui si tratti di una colonna di un attributo continuo
			if(colonna.isNumber())
			{
				//aggiungo un attributo continuo all'attribute set
				this.attributeSet.add((new ContinuousAttribute(colonna.getColumnName(), i, (float)query.getAggregateColumnValue(table, colonna, QUERY_TYPE.MIN), (float)query.getAggregateColumnValue(table, colonna, QUERY_TYPE.MAX))));
			//	System.out.println("aggiunto attributo continuo e valori");
			}
			//colonna con attributo di tipo discreto
			else
			{	
				int j = 0;
				TreeSet<String> setDiscreto = new TreeSet<>(); //così da non avere duplicati
				for (Object iteratore : query.getColumnValues(table, colonna))
				{
					if(iteratore != null)
						setDiscreto.add((String)iteratore);

				}
				String  valori_discreti [] = new String[setDiscreto.size()];
				for (String iteratore : setDiscreto)
				{
					valori_discreti[j] = iteratore;
					j++;
				}
				//aggiungo l'attributo discreto all'atribute set
				this.attributeSet.add((new DiscreteAttribute(colonna.getColumnName(), i, valori_discreti)));
		//		System.out.println("aggiunto attributo discreto e valori");
			}

		}

	////memorizzazione di tutte le tuple all'interno della matrice "data"
	Table_Data query = new Table_Data();
	List<TupleData> allTuples = query.getTransazioni(table); //prendo tutte le tuple della tabella, e le raccolgo in una lista
	data = new Object [allTuples.size()][attributesNumber]; //devo dare le dimensioni alla matrice
	for (int riga = 0; riga < allTuples.size(); riga++ )
	{
		//ciclo sulle colonne della matrice restano su una fissata riga
		for (int colonna = 0; colonna < attributesNumber; colonna++)
		{
			//uso l'indice riga allTuples.get(riga) per farmi restiruire la tupla in quella posizione,
			//infine uso l'indice colonna in (allTuples.get(riga)).tuple.get(colonna) per ottenere l'elemento in posizione "colonna" della tupla numero "riga"
			//riconosco poi a runtime il tipo dell'elemento restituito, per inserirlo appropriatamente nella matrice
			System.out.println((allTuples.get(riga)).tuple.get(colonna) ); //mi dice cosa sto prendendo
			if((allTuples.get(riga)).tuple.get(colonna) instanceof String)
			{
				data[riga][colonna] = (String)(allTuples.get(riga)).tuple.get(colonna);
			}
			else if( (allTuples.get(riga)).tuple.get(colonna) == null)
			{
				throw new NoValueException();
			}
			else
			{
				data[riga][colonna] = (float)(allTuples.get(riga)).tuple.get(colonna);
			}
	
		}
		System.out.println("///////////////////////////");
	}

	numberOfExamples = data.length;
	//		String valori_outlook [] = {"Sunny","Overcast","Rain"};
	//		//String valori_temperature [] = {"Hot", "Mild", "Cool"};
	//		String valori_humidity[] = {"High", "Normal"};
	//		String valori_wind [] = {"Weak", "Strong"};
	//		String valori_playTennis [] = {"Yes", "No"};
	//		//creare altre 4 array di valori
	//		DiscreteAttribute outlook = new DiscreteAttribute("outlook", 0, valori_outlook);
	//
	//		ContinuousAttribute temperature = new ContinuousAttribute("temperature", 1,  (float) 0.0, (float) 30.3);
	//		DiscreteAttribute humidity = new DiscreteAttribute("humidity", 2, valori_humidity);
	//		DiscreteAttribute wind = new DiscreteAttribute("wind", 3, valori_wind);
	//		DiscreteAttribute PlayTennis = new DiscreteAttribute("PlayTennis", 4, valori_playTennis);
	//
	//		attribute attributeSet [] = {outlook,temperature, humidity, wind,PlayTennis}; //array di appoggio per memorizzare gli attributi
	//		for (attribute iteratore : attributeSet)
	//		{
	//			this.attributeSet.add(iteratore); //popolamento della linked list
	//		}

}

/////////////METODI////////////////
//la traccia lo da package ma si hanno problemi con FrequentPatternMiner
//restituisce il numero di tuple
/**
 * Restituisce il numero di tuple
 * @return numero di tuple
 */
public int getNumberOfExamples ()
{
	return numberOfExamples;
}

//la traccia lo da package ma si hanno problemi con FrequentPatternMiner
/**
 * Elabora il numero degli attributi e lo da in output
 * @return numero degli attributi (cardinalità di attributeSet)
 */
public int getNumberOfAttributes()
{
	return attributeSet.size();
}
//la traccia lo da package ma si hanno problemi con FrequentPatternMiner
/**
 * Restituisce l'attributo presente nella posizione specificata in input di attributeSet
 * @param index indice dell'attributo cercato
 * @return attributo in posizione "index"
 */
public attribute getAttribute (int index)
{
	return attributeSet.get(index);
}



//la traccia lo da package ma si hanno problemi con FrequentPatternMiner
/**
 * Cerca nella matrice l'elemento nella posizione specificata in input.
 * @param exampleIndex Riga
 * @param attributeIndex Colonna
 * @return Valore presente nella posizione specificata in input
 */
public Object getAttributeValue(int exampleIndex, int attributeIndex)
{
	return data[exampleIndex][attributeIndex];
}

/**
 * Crea una stringa contenente tutti gli elementi della matrice "data[][]"
 */
public String toString()
{
	String output = new String();
	//concatena tutto in una stringa
	for (int i = 0; i<getNumberOfExamples(); i++)
	{
		for(int j = 0; j<getNumberOfAttributes(); j++)
		{
			output = output + " " + data[i][j];
		}
		output = output + "\n";
	}
	return output;
}

//metodo di supporto per testare la matrice, non richiesto dalla traccia
@SuppressWarnings("unused")
private String attributi ()
{
	String output = new String();
	Iterator<attribute> e = attributeSet.iterator();
	while (e.hasNext())
	{
		output = output + " " + " " + ((attribute)e.next()).getName();
	}
	return output.toUpperCase();
}

//metodo di supporto per confrontare coppie di attributo-valore, usato per testare la matrice 
@SuppressWarnings("unused")
private String attributo_valore(int exampleIndex, int attributeIndex)
{
	String output = new String();
	output = this.getAttribute(attributeIndex) + " = " + this.getAttributeValue(exampleIndex, attributeIndex);
	//porto tutto maiuscolo per evitare problemi nelle comparazioni
	return output.toUpperCase();	
}

////////////////////////////////////////////////////////////// MAIN /////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public static void main (String [] args) throws DatabaseConnectionException, SQLException, NoValueException
{
	try
	{
		DbAccess.closeConnection();
	}
	catch (NullPointerException e)
	{
		System.out.println("closed");
	}
	DbAccess.closeConnection();

}

}
