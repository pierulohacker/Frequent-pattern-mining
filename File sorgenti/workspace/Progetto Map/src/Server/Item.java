package Server;
import java.io.*;
/**
 * 
 * @author Masella-Stigliano
 * @version 1.0
 * <p>Company:Dipartimento di Informatica,Università degli studi di Bari </p>
 * <p>Class Description:Modella un generico Item(coppia attributo-valore,ad esempio Outlook="Sunny" e non Outlook="Yes". </p>
 *<p> Copyright:Copyright (c) 2017 </p>
 */
abstract class Item implements Serializable {

	private static final long serialVersionUID = 1L;
	protected attribute attributo;
	protected Object value;
	//COSTRUTTORE
	/**
	 * Inizializza l'attributo e il valore dell'item.
	 * @param attributo oggetto di tipo attribute
	 * @param value valore assunto dall'item
	 */
	public Item(attribute attributo, Object value) 
	{
		this.attributo = attributo;
		this.value = value;
	}
	
	//METODI
	/**
	 * Restituisce un attributo
	 * @return attributo
	 */
	attribute getAttribute()
	{
		return attributo;
	}
	/**
	 * Restituisce value
	 * @return value
	 */
	Object getValue()
	{
		return value;
	}
	
	/**
	 * Verifica se esiste un item formato dalla coppia : Attributo (membro della classe) – Value (valore passato per argomento)
	 * @param value valore assunto dall'item
	 * @return true se tale coppia esiste, false altrimenti
	 */ 
	abstract boolean checkItemCondition (Object value);
	
	/**
	 * Salva in una stringa il contenuto dell'item
	 */
	public String toString()
	{
		String output = new String();
		return output += getAttribute().toString() + " = " + "'" +  getValue().toString() + "'";
		}
	
	/**
	 * Override di equals specializzato per ogni tipo di item(discreto/continuo)
	 * @param it item con cui confrontare il chiamante
	 * @return true se uguali,false altrimenti
	 */
	//override di equals
	public abstract boolean equals (Item it);
}
