package Server;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
/**
 * 
 * @author Masella-Stigliano
 * @version 1.0
 * <p>Company:Dipartimento di Informatica,Università degli studi di Bari </p>
 * <p>Class Description: Rappresenta un pattern frequente closed. </p>
 *<p> Copyright:Copyright (c) 2017 </p>
 */
public class ClosedPattern extends FrequentPattern implements Serializable
{

	private static final long serialVersionUID = 1L;

	//COSTRUTTORE 
	/**
	 * avvalora i dati membro di uno specifico closed pattern
	 * @param fp lista di items
	 * @param support supporto del pattern di origine
	 */
	ClosedPattern (LinkedList<Item> fp, float support)
	{
		//è una prova, non ho ancora capito cosa debba fare
			super();
			this.setSupport(support);
			Iterator<Item> e = fp.iterator();
			while(e.hasNext())
			{
				this.addItem((Item)e.next());
			}
			
	}

}
