package Server;
import java.io.*;
/**
 * 
 * @author Stigliano-Masella
 * @version 1.0
 * <p>Company:Dipartimento di Informatica,Università degli studi di Bari </p>
 * <p>Class Description:Modella un intervallo di valori che un attributo continuo può assumere (Es:Temperature=[10;30.3]). </p>
 *<p> Copyright:Copyright (c) 2017 </p>
 */

public class Interval implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Attributi
	/**  estremo inferiore dell'intervallo */
	private float inf;
	/** estremo superiore dell'intervallo*/
	private float sup;
	
	///Metodi///
	/**
	 *  Avvalora i due attributi di classe inf e sup
	 * @param inf numero con cui avvalorare l'estremo inferiore dell'intervallo
	 * @param sup numero con cui avvalorare l'estremo superiore dell'intervallo
	 */
	//costruttore
	Interval(float inf, float sup)
	{
		this.inf = inf;
		this.sup = sup;
	}
	
	/**
	 * avvalora l'attributo di classe inf( invocato in situazioni differenti dal costruttore)
	 * @param inf numero con cui avvalorare l'estremo inferiore dell'intervallo
	 */
	void setInf(float inf)
	{
		this.inf = inf;
	}
    

	/**
	 * avvalora l'attributo di classe sup,(invocato in situazioni differenti dal costruttore)
	 * @param sup numero con cui avvalorare l'estremo superiore dell'intervallo
	 */
	void setSup (float sup)
	{
		this.sup = sup;
	}
	
	/**
	 * 
	 * @return inf (estremo inferiore dell'intervallo)
	 */
	float getInf()
	{
		return inf;
	}
	/**
	 * 
	 * @return sup (estremo superiore dell'intervallo)
	 */
	float getSup()
	{
		return sup;
	}
	
	/**
	 * verifica se il valore in input è incluso nell'intervallo definito dall'oggetto di classe Interval
	 * @param value valore del quale si verifica l'esistenza all'interno dell'intervallo
	 * @return true se value è incluso nell'intervallo, falso altrimenti
	 */
	boolean checkValueInclusion (float value)
	{
		if(value <= this.sup && value >= this.inf)
			return true;
		else
			return false;
	}
	
	/**
	 * concatena in un oggetto String l'intervallo formato dagli attributi di classe inf e sup
	 * @return stringa contente gli estremi dell'intervallo (Es. [20.3,30.1])
	 */
	public String toString ()
	{
		String output;
		output = "[" + inf + "," + sup + "]";
		return output;
	}

}
