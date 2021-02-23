package Server;
import java.io.*;
import java.util.Iterator;
/**
 * 
 * @author Stigliano-Masella
 * @version 1.0
 * <p>Company:Dipartimento di Informatica,Università degli studi di Bari </p>
 * <p>Class Description:  estende la classe Attribute e rappresenta un ’intervallo per un attributo continuo (numerico) ottenuto per discretizzazione </p>
 *<p> Copyright:Copyright (c) 2017 </p>
 */
public class ContinuousAttribute extends attribute implements Iterable<Float>, Serializable{
	/**
	 * ID per la serializzazione.
	 */
	private static final long serialVersionUID = 1L;
	
	/**estremo superiore di un intervallo */
	private float max;
	
	/**estremo inferiore di un intervallo*/
	private float min;
	
	//COSTRUTTORE
	/**Avvalora i dati membro della classe e quelli della superclasse attribute, invocando il supercostruttore
	 * 
	 * @param name attributo della superclasse attribute
	 * @param index attributo della superclasse attribute
	 * @param min attributo di classe
	 * @param max attributo della classe
	 */
	public ContinuousAttribute (String name, int index, float min, float max)
	{
		super (name, index);
		this.min = min;
		this.max = max;
	}
	
	//// METODI ////
	/**
	 * Restituisce il minimo valore dell'attributo.
	 * @return valore dell'attributo min
	 */
	//dovrebbe essere package, ma serve in FrequentPatternMiner
	public float getMin()
	{
		return min;
	}
	
	/** 
	 * Restituisce il massimo valore dell'attributo.
	 * @return valore dell'attributo max
	 */
	//dovrebbe essere package, ma serve in FrequentPatternMiner
	public float getMax()
	{
		return max;
	}

	@Override
	/**
	 * Istanzia e restituisce un oggetto di classe ContinuousAttributeIterator con il numero massimo
	 *  di intervalli fissato a 5
	 *  @return riferimento a un iteratore della classe ContinuousAttributeIterator con il numero massimo  di intervalli fissato a 5
	 */
	public Iterator<Float> iterator() 
	{
		return new ContinuousAttributeIterator( min, max, 5);
	}
	
	
	
	

}
