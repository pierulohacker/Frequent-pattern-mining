package Server;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
//import java.util.Set;
import java.util.TreeSet;
/**
 * 
 * @author Stigliano-Masella
 * @version 1.0
 * <p>Company:Dipartimento di Informatica,Università degli studi di Bari </p>
 * <p>Class Description:Fa da contenitore per i pattern frequenti closed con i relativi pattern ridondanti.</p>
 *<p> Copyright:Copyright (c) 2017 </p>
 */

public class ClosedPatternArchive  implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * archivio basato su una hashmap, che indicizza gli elementi (che sono TreeSet popolati dai pattern frequenti ridondanti)
	 * tramite i pattern frequenti chiusi
	 */
	HashMap<FrequentPattern, TreeSet<FrequentPattern>> archive = new HashMap<FrequentPattern, TreeSet<FrequentPattern>>();

	
	
	////////////////METODI/////////////////////////


	//costruttore
	/**
	 * Costruttore vuoto, richiesto dalla serializzazione.
	 */
	public ClosedPatternArchive() {
		
	}
	/**
	 * aggiunge il pattern frequente, se non è già contenuto.
	 * @param fp pattern frequente
	 */
	public void put (FrequentPattern fp)
	{
		if(!archive.containsKey(fp)) //dovrebbe usare l'OID dell'oggetto in input come chiave, anche per il controllo come in questo caso
		{
			//creo un nuovo treeset in cui inserisco questo fp, che poi continuerà ad essere popolato
			//con altri fp ridondanti
			TreeSet<FrequentPattern> input = new TreeSet<FrequentPattern>();
			this.archive.put(fp, input);
			//System.out.println("Inserita la chiave e un tree set vuoto");
		}
		else
			System.out.println("Frequent pattern già presente nell'archivio");
	}

	/**
	 * overload di put aggiunge il pattern frequente, se non è già contenuto. 
	 * Se invece è contenuto, inserisce l’argomento pattern all’insieme relativo al pattern-chiave.
	 * @param fp pattern frequente usato come chiave
	 * @param pattern pattern frequente da inserire nella posizione indicata dal pattern frequente chiave
	 */
	public void put(FrequentPattern fp, FrequentPattern pattern)
	{
//		if(!archive.containsKey(fp))			
//			this.put(fp);
//		else
//		{
			//System.out.println("Inserita chiave + elemento");
			//prendo il treeset corrispondente a quella chiave(fp) e vi aggiungo pattern
			//in caso pattern fosse già presente, la politica di inserimento del set impedisce l'aggiunta di duplicati
			this.archive.get(fp).add(pattern);
			System.out.println("aggiunto il ridondante : " + pattern);
			System.out.println("il nuovo cazzo di albero : " + this.archive.get(fp));
		//}

	}

	@SuppressWarnings("unchecked")
	/**
	 * restituisce tutti i ridondanti di un frequent pattern preso in input
	 * @param fp pattern frequente di cui restituire i ridondanti
	 * @return clone del treeset contenente i ridondanti del frequent pattern in input (clonato per evitare modifiche al di fuori del calcolo rigoroso dei closed pattern basato sull'algoritmo di scoperta)
	 * @throws NoKeyException nell'archivio non è presente la chiave specificata in input
	 */
	public TreeSet<FrequentPattern> getRedundants (FrequentPattern fp) throws Exception
	{
		if(archive.containsKey(fp))
		{
			if(archive.get(fp).size() >= 1) //vuol dire che c'è almeno un ridondante
			{
				//clono solo la struttura e non gli elementi contenuti, così da poter manipolare la disposizione degli elementi
				// e non gli elementi veri e propri.
				TreeSet<FrequentPattern> clone = (TreeSet<FrequentPattern>)this.archive.get(fp).clone();
				//ora su questo clone posso controllare se ci siano altri elementi oltre ad fp, così lo rimuovo e restituisco un albero popolato solamente dai pattern ridondanti
				clone.remove(fp);//rimuovo fp poichè voglio solo i ridondanti
				return clone;
			}
			else
			{
				System.out.println("Non ci sono pattern ridondanti");
				return archive.get(fp);
			}
		}
		else 
		{
			throw new NoKeyException();
		}
	}


	/**
	 * Scandendo le chiavi con un iteratore per FrequentPattern crea una String concatenando un pattern frequente closed al suo insieme di pattern frequenti ridondanti.
	 */
	public String toString()
	{
		String output = new String();
		int i = 0;
		System.out.println("DIMENSIONE DELLA HASHMAP : " + this.archive.size());
		//Iterator<FrequentPattern> iteratore = keys.iterator(); //iteratore per il set di chiavi dell'archivio
		for(FrequentPattern iteratore : this.archive.keySet())
		{
		 	i++;
			output += "[" + i + "] " +  "CHIAVE : " + (FrequentPattern)iteratore + "\n";
			Iterator<FrequentPattern> iterTree = archive.get(iteratore).iterator();
			while(iterTree.hasNext())
			{
				output = output + (FrequentPattern)iterTree.next() +  "\n";
			}
				
			output += "\n -------------------------------------------\n";
		}
		return output;
	}
	
	

	/**
	 * Effettua il salvataggio dell'archivio contente pattern chiusi e rispettivi pattern frequenti,
	 * se il path è un null, l'archivio sarà salvato di default nel workspace del progetto
	 *@param path percorso in cui salvare il file. Se il valore è null,viene salvato nella directory di default (workspace) 
	 * @param fileName nome del file che sarà creato
	 * @throws IOException Problemi con lo stream, dovuti a errori nel salvataggio.
	 */
	public void save (String path, String fileName) throws IOException
	{	
		if(path == null) //se il path è un null, l'archivio sarà salvato di default nel workspace del progetto
		{
			FileOutputStream outFile = new FileOutputStream(fileName);
			ObjectOutputStream outStream = new ObjectOutputStream(outFile);
			outStream.writeObject(this);
			outStream.close();

		}
		else
		{
			File dir = new File (path);//directory in cui salvare
			dir.mkdirs();
			FileOutputStream outFile = new FileOutputStream(path + "//" + fileName);
			ObjectOutputStream outStream = new ObjectOutputStream(outFile);
			outStream.writeObject(this);
			outStream.close();
		}
	}

	/**
	 * Effettua il caricamento di uno specifico archivio.
	 * 
	 * @param path percorso in cui cercare l'archivio. Se ha valore null, il file viene cercaro nella directory di default (workspace)
	 * @param fileName nome del file da caricare
	 * @return Archivio caricato
	 * @throws IOException Problemi con lo stream, dovuti a errori nel salvataggio.
	 * @throws ClassNotFoundException la classe da caricare non è stata trovata 
	 */
	public static ClosedPatternArchive load (String path, String fileName) throws IOException, ClassNotFoundException
	{
		if(path == null) //se il path è un null, l'archivio sarà cercato e caricato  di default dal workspace del progetto
		{
			FileInputStream inFile = new FileInputStream(fileName);
			ObjectInputStream inStream = new ObjectInputStream(inFile);
			ClosedPatternArchive out = (ClosedPatternArchive) inStream.readObject();
			inStream.close();
			return out;
		}
		else
		{
			FileInputStream inFile = new FileInputStream(path + "//" + fileName);
			ObjectInputStream inStream = new ObjectInputStream(inFile);
			ClosedPatternArchive out = (ClosedPatternArchive) inStream.readObject();
			inStream.close();
			return out;
		}
	}
}
