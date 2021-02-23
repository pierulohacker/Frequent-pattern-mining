package Server;


import java.util.Iterator;
import java.util.LinkedList;
//import java.util.TreeSet;
import java.io.*;

/**
 * 
 * @author Stigliano-Masella
 * @version 1.0
 * <p>Company:Dipartimento di Informatica,Università degli studi di Bari </p>
 * <p>Class Description:Modella la scoperta di pattern closed a partire da pattern frequenti calcolati in precedenza. </p>
 *<p> Copyright:Copyright (c) 2017 </p>
 */

public class  ClosedPatternMiner implements Serializable, Irrilevancy
{

	private static final long serialVersionUID = 1L;

	///METODI///
	/**
	 * Costruttore vuoto, richiesto dalla serializzazione.
	 */
	public ClosedPatternMiner() {

	}

	/**
	 * SCOPERTA DI PATTERN CHIUSI
	 *Iterando sull'insieme di pattern frequenti, verifica quali soddisfano le due condizioni di chiusura. La associazione patternfrequente
	 *ridondante e pattern-closed può anche non essere esclusiva (uno stesso pattern frequente può essere coperto da più pattern
	 *closed). La verifica delle condizioni di chiusura è fatta da metodi appositi sotto specificati.
	 * @param outFP contenitore di coppie pattern-closed,pattern frequenti	ridondanti
	 * @param epsilon valore minimo di chiusura
	 * @return archivio contenente i pattern chiusi con i rispettivi pattern ridondanti
	 */
	public static ClosedPatternArchive closedPatternDiscovery(LinkedList<FrequentPattern> outFP,float epsilon)
	{
		ClosedPatternMiner irrilevant = new ClosedPatternMiner();
		irrilevant.pruneSingle(outFP);
		irrilevant.prunePermutations(outFP);
		////ho eliminato livello 1 e permutazioni

		ClosedPatternArchive archivioOutput = new ClosedPatternArchive(); //archivio che conterrà i closed pattern con i corrispondeni ridondanti
		for(FrequentPattern outFPiter : outFP)
		{
			if(!outFPiter.isClosed && !outFPiter.isRedundant)
			{
				//prendo il pattern più lungo in questo ciclo, e lo confronto con tutti quelli più brevi che potrebbero essere suoi sottoinsiemi
				// e quindi dei ridondanti (quindi saranno necessariamenti più corti)
				ClosedPattern candidate = new ClosedPattern((LinkedList<Item>)outFPiter.getItemList(), outFPiter.getSupport());
				int maxLength = candidate.getPatternLength();
				int currentLength = maxLength;
				System.out.println("//////////////////////////////\nCANDIDATO: " + candidate);
				while (currentLength > 1)
				{
					currentLength--;
					//devo porre temporaneamente i frequent pattern di lunghezza currentLenght in una struttura di candidati ridondanti				
					//struttura atta ad ospitare candidati ridondanti con lunghezza pari a current length
					//uso un treeset poichè è la struttura presa in input per la HashMap
					LinkedList<FrequentPattern> redundant_candidates = new LinkedList<FrequentPattern>();
					FrequentPattern temp = new FrequentPattern();
					for(FrequentPattern iteratore : outFP)////////////////////////////////////////////
					{				
						if(((FrequentPattern) iteratore).getPatternLength() == currentLength)
						{
							redundant_candidates.add((FrequentPattern)iteratore);
						}
					}				
					Iterator<FrequentPattern> iteratoreTree = redundant_candidates.iterator();
					//ciclo su ogni elemento ridondate
					while(iteratoreTree.hasNext())		     //for (r € redundant candidates)		
					{
						temp = iteratoreTree.next();
						if(isCoveredByItems(candidate, temp ) && isCoveredBySupport(candidate, temp, epsilon))
						{
							//	System.out.println("CONDIZIONI DI CHIUSURA VERIFICATE");
							//soddisfatte le condizioni, possiamo inserire i dati nell'archivio dei closed + ridondanti
							archivioOutput.put(candidate);
							archivioOutput.put(candidate,temp);	
							candidate.isClosed = true;
							temp.isRedundant = true;
							System.out.println(temp);
						}
					}

				}

			}


		}
		for(FrequentPattern outFPiter : outFP)
			if(outFPiter.isRedundant == false && outFPiter.getPatternLength() == 2 && outFPiter.getSupport() < epsilon)
				archivioOutput.put(outFPiter);
		return archivioOutput;
	}


	/**
	 * Verifica se i pattern rendono vera la condizione di chiusura rispetto agli items contenuti. 
	 * In particolare, iterando sugli item ne riconosce il tipo e, in caso di item discreti, verifica se i valori discreti coincidono, 
	 * mentre in caso di item continui, verifica se un intervallo numerico contiene (non strettamente) l'altro intervallo.
	 * @param current pattern candidato closed
	 * @param examined pattern candidato ridondante
	 * @return true se la condizione di chiusura rispetto agli item è soddisfatta, false altrimenti
	 */
	private static boolean isCoveredByItems(FrequentPattern	current,FrequentPattern examined)
	{
		int counter = 0;
		for(Item iteratoreExamined : examined)
		{
			for(Item iteratoreCurrent : current)
			{
				//CASO SPECIALIZZATO PER ITEM DISCRETI
				if((Item)iteratoreCurrent instanceof DiscreteItem && (Item)iteratoreExamined instanceof DiscreteItem)
				{
					if(((Item)iteratoreCurrent).equals((Item)iteratoreExamined))
					{
						counter ++;
					}
				}
				//CASO SPECIALIZZATO PER ITEM CONTINUI
				if((Item)iteratoreCurrent instanceof ContinuousItem && (Item)iteratoreExamined instanceof ContinuousItem)
				{
					if(iteratoreCurrent.getAttribute().equals(iteratoreExamined.getAttribute()) && 
							((ContinuousItem)iteratoreCurrent).checkItemCondition(iteratoreExamined.value))

					{
						counter ++;
					}
				}
			}
		}
		if(counter == examined.getPatternLength()) //se counter è uguale alla lunghezza del pattern allora tutti gli item sono uguali
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	//SECONDA CONDIZIONE DI CHIUSURA
	//Verifica se i pattern rendono vera la condizione di chiusura rispetto al supporto.
	/**
	 * Verifica se i pattern rendono vera la condizione di chiusura rispetto al supporto, basata sulla differenza tra
	 * il supporto del pattern corrente e quello del pattern esaminato, la quale deve essere minore di epsilon
	 * @param current pattern frequente corrente
	 * @param examined pattern frequente esaminato
	 * @param epsilon valore da confrontare con la differenza dei supporti
	 * @return true se la differenza è minore, false altrimenti
	 */
	private static boolean isCoveredBySupport(FrequentPattern current,FrequentPattern examined, float epsilon)
	{
		//if(Math.abs(examined.getSupport() - current.getSupport()) < epsilon)
		if((examined.getSupport() - current.getSupport()) < epsilon)
			return true;

		else
			return false;
	}


	//restituisce il closed pattern di lunghezza maggiore
	/**
	 * (Estensione)
	 * Trova il pattern con lunghezza maggiore, data una lista di pattern frequenti
	 * @param FP lista di pattern frequenti
	 * @return pattern frequente più lungo
	 */
	@SuppressWarnings("unused")
	private static FrequentPattern getMaxLengthFP (LinkedList<FrequentPattern> FP)
	{
		int max = 0;
		FrequentPattern outputFP = new FrequentPattern();
		FrequentPattern temp = new FrequentPattern();
		int candidate; //lunghezza da confrontare ogni volta con max
		Iterator<FrequentPattern> iteratore = FP.iterator();
		while(iteratore.hasNext())
		{
			temp = iteratore.next();//prendo la lunghezza del pattern attuale
			candidate = temp.getPatternLength();
			if(candidate > max)
			{
				max = candidate;
				outputFP = temp;
			}
		}
		return outputFP;
	}


	/**
	 * Elimina tutti i pattern di lunghezza 1 dalla lista di pattern frequenti che riceve in input
	 * @param outFP Lista di pattern frequenti
	 */
	public void pruneSingle(LinkedList<FrequentPattern> outFP) 
	{
		Iterator<FrequentPattern> iteratore = outFP.iterator(); //iteratore generico per ciclare all'interno della linked list su pattern frequenti
		// in essa contenuti
		while (iteratore.hasNext())
			if(iteratore.next().getPatternLength() == 1)
				iteratore.remove();
		//		for (FrequentPattern iteratore : outFP)
		//		{
		//			if(iteratore.getPatternLength() == 1)
		//				outFP.remove(iteratore);
		//		}
	}

	/**
	 * Elimina tutte le permutazioni presenti nella lista di pattern frequenti
	 * (es. outlook = sunny AND temperature = high vs. temperature = high AND outlook = sunny)
	 * @param outFP Lista di pattern frequenti
	 */
	public void prunePermutations(LinkedList<FrequentPattern> outFP) 
	{
		LinkedList<FrequentPattern> noPerm = new LinkedList<>();
		Iterator<FrequentPattern> iteratoreOutFisso = outFP.iterator(); //iteratore generico della linked list per ciclare sui pattern frequenti

		while (iteratoreOutFisso.hasNext())
		{
			Iterator<FrequentPattern> iteratoreOutIterante = outFP.iterator(); //altro iteratore che cicla sul fissato di sopra
			FrequentPattern  fisso = iteratoreOutFisso.next();
			while(iteratoreOutIterante.hasNext())
			{
				FrequentPattern iterante = iteratoreOutIterante.next();
				if( !fisso.isPermutation && fisso.equalsPermutations(iterante))
				{
					if(!noPerm.contains(fisso))
						noPerm.add(fisso);
					fisso.hasPermutations = true;
					iterante.isPermutation = true;
				}
			}
		}
		outFP.clear();

		outFP.addAll(noPerm);
	}
}

