package Server;

import java.util.Iterator;
/**
 * 
 * @author Masella-Stigliano
 * @version 1.0
 * <p>Company:Dipartimento di Informatica,Università degli studi di Bari </p>
 * <p>Class Description:Modella l'iterazione sulla collezione di intervalli di valori che l'attributo continuo può assumere. </p>
 *<p> Copyright:Copyright (c) 2017 </p>
 */

public class ContinuousAttributeIterator implements Iterator<Float> 
{
	///ATTRIBUTI///
	/**
	 * valore minimo dell'attributo
	 */
	private float min; 

	/**
	 * valore massimo dell'attributo
	 */
	private float max; 

	/**
	 *  j-esimo elemento della collezione, ovvero elemento 	corrente della iterazione
	 */
	private int j=0;

	/**
	 *  numero di elementi complessivi (numero di intervalli di valori)
	 */
	private int numValues;

	///METODI///

	//costruttore
	/**
	 * Avvalora gli attributi della  min, max, numValues della classe con i rispettivi
	 * @param min valore da assegnare all'attributo min della classe
	 * @param max valore da assegnare all'attributo sup della classe
	 * @param numValues numero massimo degli intervalli da ottenere dalla discretizzazione
	 */
	ContinuousAttributeIterator (float min, float max, int numValues)
	{
		this.min = min;
		this.max = max;
		this.numValues = numValues;
	}
	
	@Override
	/**
	 * verifica che il j-esimo elemento abbia un successivo
	 */
	public boolean hasNext() 
	{
		if(j < numValues)
			return true;
		else
			return false;
	}

	@Override
	/**Calcola e restituisce l'estremo superiore di ogni sottointervallo
	 * @return estremo superiore del sottointervallo j-esimo
	 */
	public Float next() 
	{
		j++;
		return new Float(min + (j * (max-min)/numValues));
	}



}
