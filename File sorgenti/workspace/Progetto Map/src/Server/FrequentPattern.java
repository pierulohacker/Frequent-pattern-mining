package Server;
import java.io.*;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 * @author Masella-Stigliano
 * @version 1.0
 * <p>Company:Dipartimento di Informatica,Università degli studi di Bari </p>
 * <p>Class Description:Rappresenta un itemset frequente. </p>
 *<p> Copyright:Copyright (c) 2017 </p>
 */
public class FrequentPattern  implements Iterable<Item>, Comparable<FrequentPattern>, Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//ATTRIBUTI
	/** linked list che contiene gli oggetti Item che definiscono il pattern  */
	private LinkedList<Item> fp = new LinkedList<Item>(); 
	
	/**
	 * valore di supporto (frequenza relativa)
	 */
	private float support;
	
	/**Attributo che indica se il pattern preso in considerazione è chiuso*/
	boolean isClosed = false;
	
	/**
	 * specifica se il pattern sia ridondante o meno.
	 */
	boolean isRedundant = false;
	
	/**
	 * Attributo atto a marcare pattern che hanno delle permutazioni, così da semplificare ed ottimizzare i cicli su liste di pattern frequenti
	 * quando si vogliono rimuovere le permutazioni.
	 */
	boolean hasPermutations = false;
	/**
	 * Attributo atto a specificare se tale pattern è la permutazione di un altro
	 */
	boolean isPermutation = false;

	//COSTRUTTORE richiesto per la serializzazione
	public FrequentPattern() {
	}

	//METODI/////////////////

	void addItem (Item item)
	{
		this.fp.add(item);
	}

	Item getItem (int index)
	{
		return fp.get(index);
	}

	/**
	 * Verifica se due pattern sono uguali, confrontando item per item
	 * @param fp pattern frequente da confrontare con il pattern frequente chiamante
	 * @return true se i due pattern sono uguali, false altrimenti
	 */
	//IMPLEMENTAZIONE DI EQUALS FUNZIONALE AI CONFRONTI IN FREQUENTPATTERNMINER
	public boolean equals(FrequentPattern fp)
	{
		if (this.getPatternLength()  != fp.getPatternLength())
		{
			//lunghezze diverse
			return false;
		}
		else
		{
			boolean flag = false;
			for(int i = 0; i < this.getPatternLength(); i++)
			{
				if(!this.fp.get(i).equals(fp.getItem(i))) 
				{
					flag = false;
					break;
				}
				else
					flag = true;
			}
			return flag;
		}
	}
/**
 * Verifica se due pattern sono uguali SOLO per permutazione.
 * @param fp pattern frequente da confrontare con il pattern frequente chiamante
 * @return true se i due pattern sono uguali per permutazione, false altrimenti
 */
	public boolean equalsPermutations(FrequentPattern fp)
	{
		if (this.getPatternLength()  != fp.getPatternLength())
		{
			//lunghezze diverse
			return false;
		}
		else if(!fp.equals(this))
		{

			int count = 0;
			for(Item e : this.fp)
			{
				for(Item iter : fp)
				{
					if(((Item)e).equals((Item)iter))
					{
						count ++; //FONDAMENTALE!! ci serve a sapere che gli item uguali sono in quantità pari alla lunghezza del pattern
						break;
					}
				}
			}
			if (count == fp.getPatternLength())
				return true;
			else
				return false;
		}
		else
			return false;
	}
	/**
	 * Restituisce il supporto
	 * @return support
	 */
	float getSupport()
	{
		return support;
	}
	/**
	 * Restituisce la dimensione dell'array fp
	 * @return fp.size();
	 */
	int getPatternLength()
	{
		return fp.size();
	}
	/**
	 * Assegna support al membro attributo
	 * @param support valore numerico che indica la frequenza assoluta
	 */
	void setSupport(float support) 
	{
		this.support = support;
	}
	/**
	 * Scandendo l'array fp concatena gli item aggiungendo infine il valore del membro attributo support
	 */
	public String toString() 
	{
		Iterator<Item> e = fp.iterator();
		String value=new String();
		//ciclo sfruttando l'iterator, anche se qualcosa sembra da sistemare
		for(int i=0;i<fp.size()-1;i++)
		{
			value+=((Item)e.next()) +" AND ";

		}
		if(fp.size()>0)
		{
			value+=fp.get(fp.size()-1);
			value+=" ("+support+")";

		}

		return value;
	}
	/**
	 * Effettua una clonazione di fp
	 */
	public FrequentPattern clone()
	{
		//deep copy
		FrequentPattern clone = new FrequentPattern();
		//clonazione con iteratore generico come richiesto dal prof
		Iterator<Item> it = fp.iterator();
		while(it.hasNext())
		{
			clone.fp.add((Item)it.next());
		}
		clone.setSupport(this.getSupport());
		return clone;
	}
	/**
	 * Restituisce il riferimento al contenitore degli items del pattern
	 * @return fp
	 */
	public LinkedList<Item> getItemList()
	{
		return this.fp;
	}

	/**
	 * Implementazione dell'iteratore
	 * @author Stigliano-Masella
	 *
	 */
	class iterFP implements Iterator<Item>
	{
		int count = 0;
		public boolean hasNext()
		{
			return count < fp.size(); 
		}
		public Item next()
		{
			Item output = (Item)fp.get(count);
			count++;
			return output;	
		}

	}
	/**
	 * Restituisce l'iteratore
	 */
	public Iterator<Item> iterator()
	{
		return new iterFP();
	}

	@Override
	public int compareTo(FrequentPattern fp) 
	{
		return fp.getPatternLength() + this.getPatternLength();
	}

 public static void main(String[] args) 
	{
		@SuppressWarnings("unused")
		FrequentPattern pattern1 = new FrequentPattern();
		@SuppressWarnings("unused")
		FrequentPattern pattern2 = new FrequentPattern();
		String values [] = new String [2];
		values[0] = "sereno";
		values[1] = "coperto";
		DiscreteAttribute attr1 = new DiscreteAttribute("cielo", 0, values);
		Item item1 = new DiscreteItem(attr1, "sereno");
		System.out.println(item1);
	}
	
}

