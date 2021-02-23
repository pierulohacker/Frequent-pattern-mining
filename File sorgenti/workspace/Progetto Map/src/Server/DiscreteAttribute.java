package Server;

import java.io.*;
/**
 * 
 * @author Stigliano-Masella
 * @version 1.0
 * <p>Company:Dipartimento di Informatica,Università degli studi di Bari </p>
 * <p>Class Description: Estende la classe Attribute e rappresenta un attributo discreto </p>
 *<p> Copyright:Copyright (c) 2017 </p>
 */
public class DiscreteAttribute extends attribute implements Serializable
	{
	private static final long serialVersionUID = 1L;
	
	///ATTRIBUTI///
	/** array contenente i valori degli attributi discreti */
	private String values[]; 

	//costruttore

	/**
	 *  avvalora i dati membro della classe e quelli della superclasse attribute, invocando il supercostruttore
	 * @param name attributo della superclasse attribute
	 * @param index attributo della superclasse attribute
	 * @param values vettore 
	 */
	public DiscreteAttribute(String name,int index, String values []) {
		super (name, index);
		this.values = values;	
	}
	
	//la traccia lo da package ma si hanno problemi con FrequentPatternMiner
	/** @return cardinalità di values */
	public int getNumberOfDistinctValues ()
	{
		return values.length;
	}
	
	//la traccia lo da package ma si hanno problemi con FrequentPatternMiner
	
	/**
	 * 
	 * @param i indice atto a designare l'elemento da restituire dal vettore
	 * @return i-esimo elemento del vettore values
	 */
	public String getValue(int i)
	{
		return this.values[i];
	}
	
	

}
