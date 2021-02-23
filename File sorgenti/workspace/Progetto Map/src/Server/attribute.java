package Server;

import java.io.Serializable;
/**
 * 
 * @author Stigliano-Masella
 * @version 1.0
 * <p>Company:Dipartimento di Informatica,Università degli studi di Bari </p>
 * <p>Class Description:Modella un generico attributo discreto o continuo </p>
 *<p> Copyright:Copyright (c) 2017 </p>
 */
public abstract class attribute implements Serializable {

	private static final long serialVersionUID = 1L;
	/**nome simbolico dell'attributo*/
	protected String name; 
	/** identificativo numerico dell'attributo */
	protected int index; 
	
	//costruttore
	/**
	 * inizializza i dati membro della classe
	 * @param name attributo della classe
	 * @param index attributo della classe
	 */
	attribute(String name, int index)
	{
		this.name = new String (name);
		this.index = index;
	}


	//METODI ///
	
	/**
	 * 
	 * @return valore del membro name
	 * (quindi il nome di un attributo) 
	 */
	 
	String getName()
	{
		return name;
	}
	
	//la traccia lo da package ma si hanno problemi con FrequentPatternMiner
	/**
	 * 
	 * @return valore del membro index
	 */
	public int getIndex()
	{
		return index;
	}
	
	/**restituisce il valore del membro name, usato per la comunicazione con l'esterno
	 * (dato che getName è private ed è usato diversamente)
	 */
	public String toString() 
	{
		return name;
	}
	
	//override di EQUALS per il confronto tra attributi
	/**compara due attributi verificando che nome e indice siano uguali
	 * 
	 * @param input attributo preso in input per il confronto con l'oggetto attributo che invoca il metodo
	 * @return true se l'uguaglianza è verificata, false altrimenti
	 */
	public boolean equals(attribute input)
	{
		if (input.getName().equals(this.getName()) && input.getIndex() == this.getIndex())
			return true;
		else
			return false;
	}
}
