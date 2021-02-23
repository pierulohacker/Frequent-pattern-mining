package Server;
import java.util.Iterator;

/**
 * 
 * @author Masella-Stigliano
 * @version 1.0
 * <p>Company:Dipartimento di Informatica,Università degli studi di Bari </p>
 * <p>Class Description:Modella la scoperta di patter frequenti(basato sull'algoritmo "frequentPatternDiscovery"). </p>
 *<p> Copyright:Copyright (c) 2017 </p>
 */
public class FrequentPatternMiner {
	
	
	////////////////////////////// METODI ///////////////////////////////////////////////
	
	/**Costruttore richiesto dalla serializzazione. */
	public FrequentPatternMiner() {
		// TODO Auto-generated constructor stub
	}
	
	/**Genera tutti i pattern k=1 frequenti e da questi quelli
	 * con k maggiore di 1. Scandendo gli attributi e riconoscendone il tipo crea pattern
	 *di un item con l’attributo corrente (discreto, continuo) e i possibili valori
	 *(discreti, intervalli) che può assumere: per ciascuno di essi ne calcola il
	 *supporto e, nel caso sia frequente, viene considerato per il passo k=2. I
	 *pattern frequenti con k maggiore di 1 sono calcolati con metodo apposito.
	 * 
	 * @param data matrice contenente tutte le transizioni per l'analisi dei pattern
	 * @param minSup supporto minimo
	 * @return lista linkata contente tutti i frequent pattern
	 * @throws EmptySetException l'insieme della transizioni (data) è vuoto
	 */
	public static LinkList frequentPatternDiscovery(Data data,float minSup) throws EmptySetException
	{
		Queue<FrequentPattern> fpQueue=new Queue<FrequentPattern>();	//struttura che contiene i pattern frequenti a livello k per generare i pattern frequenti a livello k+1	
		LinkList outputFP=new LinkList(); //lista che conterrà i pattern frequenti
		//System.out.println("INIZIO DEL PRIMO FOR IN DISCOVERY");
		if (data.getNumberOfExamples() == 0)
		{
			throw new EmptySetException(); 
		}
		for(int i=0;i<data.getNumberOfAttributes();i++)
		{
			System.out.println("\n ---------------------- CICLO SU " + data.getAttribute(i)+ " -------------------------------  \n");
			//prendo il primo attributo dall'array attributeset
			attribute currentAttribute=data.getAttribute(i);
			//tramite instanceof vediamo dinamicamente quale sia il tipo dell'oggetto (discreto o continuo), per specializzare il raffinamento dei pattern
			//      ***ATTRIBUTO DISCRETO***
			if(currentAttribute instanceof DiscreteAttribute) //RTTI
			{
				for(int j=0;j<((DiscreteAttribute)currentAttribute).getNumberOfDistinctValues();j++)
				{
					//prendo l'attributo e il valore correnti
					DiscreteItem item=new DiscreteItem(	(DiscreteAttribute)currentAttribute, ((DiscreteAttribute)currentAttribute).getValue(j));
					//genera pattern con questo item e calcola il supporto
					FrequentPattern fp=new FrequentPattern(); 
					//popolamento del frequent pattern
					fp.addItem(item);
					//System.out.println("SETTAGGIO DEL SUPPORTO \n");
					fp.setSupport(computeSupport(data, fp));
					///////////////// fine codice prof//////////////////////////////////////////////////////////////////
					//se il supporto calcolato per questi item è >= al minimo, allora viene messo nella coda dei pattern frequenti a livello 1, dai quali si partirà
					//per creare dei pattern frequenti di livello > 1
					if (fp.getSupport() >= minSup) 
					{
						fpQueue.enqueue(fp);
						System.out.println(fp);
						outputFP.add(fp);
					}
				}
			}
			//    ***ATTRIBUTO CONTINUO***  
			else
			{
				float temp_min = ((ContinuousAttribute)currentAttribute).getMin();
				Iterator<Float> continuousIt = ((ContinuousAttribute)currentAttribute).iterator();
				while (continuousIt.hasNext())
				{
					//prendo l'attributo e il valore correnti
					ContinuousItem item=new ContinuousItem(	(ContinuousAttribute)currentAttribute, new Interval(temp_min, continuousIt.next()));
					//genera pattern con questo item e calcola il supporto
					FrequentPattern fp=new FrequentPattern(); 
					//popolamento del frequent pattern
					fp.addItem(item);
				//	System.out.println("SETTAGGIO DEL SUPPORTO \n");
					fp.setSupport(computeSupport(data, fp));
					///////////////// fine codice prof
					//se il supporto calcolato per questi item è >= al minimo, allora viene messo nella coda dei pattern frequenti a livello 1, dai quali si partirà
					//per creare dei pattern frequenti di livello > 1
					if (fp.getSupport() >= minSup)
					{
						fpQueue.enqueue(fp);
						outputFP.add(fp);
					}
					//temp_min assume il valore dell'attuale massimo, che è il minimo dell'intervallo successivo
					temp_min = ((Interval)item.getValue()).getSup();
				}
			}
		}
	//	System.out.println("\n ---------------------- COMPUTATO IL LIVELLO 1 -------------------------------  \n"); 
	//	System.out.println("\n ***AVVIO ESPANSIONE DEI PATTERN DI LIVELLO SUPERIORE A 1*** \n");
		outputFP=expandFrequentPatterns(data,minSup,fpQueue,outputFP);
	//	System.out.println("\n ***DOPO EXPAND, TORNA IL CONTROLLO A DISCOVERY*** \n\n");
		return outputFP;
	}
	/**
	 * Estende ciascun pattern in fpQueue e ne verifica il
	 *supporto. Scandendo gli attributi e riconoscendone il tipo, verifica se
	 *l’attributo corrente è già presente (con qualsiasi valore discreto o
	 *intervallo) nel pattern corrente di fpQueue: se è già presente allora
	 *l’attributo corrente non può essere considerato per ampliare il pattern.
	 *Altrimenti estrae dall’ attributo ciascun Item identificando Attributo e
	 *Valore o Intervalli di Valori associati, raffinando così il pattern. Calcola il
	 *supporto del pattern così raffinato/ampliato verificandone la frequenza:
	 *nel caso è frequente, viene considerato per il passo k successivo.
	 * @param data insieme delle transizioni
	 * @param minSup supporto minimo
	 * @param fpQueue  pattern frequenti da estendere
	 * @param outputFP  pattern frequenti per ogni valore di k
	 * @return  lista linkata popolata con pattern frequenti a k maggiore di 1
	 */
	//
	private static   LinkList expandFrequentPatterns(Data data, float minSup, 	Queue<FrequentPattern> fpQueue,LinkList outputFP)
	{
		//System.out.println("\n INIZIO EXPAND");
		while (!fpQueue.isEmpty())
		{
			FrequentPattern fp=(FrequentPattern)fpQueue.first();
			//System.out.println("\n ///////////////////////// ESPANSIONE A PARTIRE DA : " + fpQueue.first()+"  /////////////////////////  \n"); 
			fpQueue.dequeue();
			/*tutto un controllo per vedere se è possbile trovare attributi della tabella che non siano presenti
			all'interno di fp, e quindi sono dei candidati per l'espansione*/
			//cicla per il numero di attributi della matrice
			//System.out.println("Entro nel primo for di EXPAND");
			for(int i=0;i<data.getNumberOfAttributes();i++)
			{
				boolean found=false;
				//controlla ogni attributo di fp con un attributo fissato della matrice
				for(int j=0;j<fp.getPatternLength();j++) 
				{
					if(fp.getItem(j).getAttribute().equals(data.getAttribute(i)))
					{
						found=true;
						break; //poichè non ci devono mai essere attributi duplicati
					}
				}
				if(found == false) //caso in cui l'attributo non è uguale all'iesimo attributo della matrice
				{
				//	System.out.println("l'attributo " + data.getAttribute(i) + " non è presente in fp");

					//SPECIALIZZO IL PROCESSO PER ATTRIBUTI DISCRETI
					if(data.getAttribute(i) instanceof DiscreteAttribute)
					{
						for(int j=0;j<((DiscreteAttribute)data.getAttribute(i)).getNumberOfDistinctValues();j++)
						{

							DiscreteItem item=new DiscreteItem((DiscreteAttribute)data.getAttribute(i),	((DiscreteAttribute)(data.getAttribute(i))).getValue(j));
							FrequentPattern newFP  = FrequentPatternMiner.refineFrequentPattern(fp,item);
							//quando il raffinamento non va a buon fine poichè l'attributo è già presente,
							//si effettuano operazioni inutili, che non porteranno OVVIAMENTE ad alcuna modifica del fp
							//c'è un modo per risparmiare su questa computazione?
							newFP.setSupport(FrequentPatternMiner.computeSupport(data,newFP));
						//	System.out.println("Setto il supporto di newFP che è " +newFP.toString());

							//fine codice professore
							//*[togliendo la negazione ho quelli di lv 1]*
							//NON DEVO CONFRONTARE CON FP, ALTRIMENTI AVRò SEMPRE ANCHE LE PERMUTAZIONI!!!
							Puntatore p=outputFP.firstList(); //puntatore atto a ciclare sulla linklist fornita dal prof
							//RIMUOVENDO QUESTO CICLO HO TUTTI I FP INCLUSE LE PERMUTAZIONI
							boolean isEqual = false;
							while(!outputFP.endList(p))
							{//controllo che il pattern newFP non sia già presente all'interno della lista di pattern frequenti già calcolati

								FrequentPattern FP=(FrequentPattern)outputFP.readList(p);
								if(!FP.equals(newFP))
								{
									isEqual = false;

								}
								else
								{
									isEqual = true;
					//				System.out.println(FP  +  "  è uguale a   " + newFP);
									break;
								}
								p=outputFP.succ(p);		
							}
							if (newFP.getSupport() >= minSup  && isEqual == false) 
							{	
							//	System.out.println("il supporto " + newFP.getSupport() + " è maggiore o uguale a " + minSup);
								//fp = newFP;   //|||NOTA: questo assegnamento provoca una alterazione, poichè fp deve solo ospitare il frequent pattern preso dalla coda
								//				|||		 mentre così esso viene modificato nel corso dei cicli, portano a risultati parziali e alterati
								fpQueue.enqueue(newFP);
								outputFP.add(newFP);
							//	System.out.println("Ho aggiunto ai pattern frequenti => " + newFP.toString());
								//break;
							}
							////EDIT : a quanto pare il break interrompeva altri tentativi di creare combinazioni potenzialmente utili, riducendo il numero dei pattern frequenti
							/// 	   o adirittura alterandone alcuni
							/*else
						{
							System.out.println("!!EXPAND non effettuato!! ");
							//--------------SE USO IL BREAK HO UN RISULTATO IN MENO E ALCUNI SONO DIFFERENTI!! CAPIRE IL PERCHE!-----------------------------
							//poichè si continuerebbe a tentare di fare espansioni già errate in partenza
							break;
						}*/
						}
					}

					//SPECIALIZZO IL PROCESSO PER ATTRIBUTI CONTINUI
					else
					{
						Iterator<Float> continuousIt = ((ContinuousAttribute)data.getAttribute(i)).iterator();
						float temp_min = ((ContinuousAttribute)data.getAttribute(i)).getMin();
						while (continuousIt.hasNext())
						{
							ContinuousItem item=new ContinuousItem(	(ContinuousAttribute)data.getAttribute(i), new Interval(temp_min, continuousIt.next()));
							//ContinuousItem item=new ContinuousItem(	(ContinuousAttribute)data.getAttribute(i), new Interval(temp_min, ((ContinuousAttribute)data.getAttribute(i)).getMax()));
							//continuousIt.next();
							FrequentPattern newFP  = FrequentPatternMiner.refineFrequentPattern(fp,item);
							newFP.setSupport(FrequentPatternMiner.computeSupport(data,newFP));
						//	System.out.println("Setto il supporto di newFP che è " +newFP.toString());
							Puntatore p=outputFP.firstList();
							boolean isEqual = false;
							while(!outputFP.endList(p))
							{
								FrequentPattern FP=(FrequentPattern)outputFP.readList(p);
								if(!FP.equals(newFP))
								{
									isEqual = false;
								}
								else
								{
									isEqual = true;
									break;
								}
								p=outputFP.succ(p);		
							}
							if (newFP.getSupport() >= minSup  && isEqual == false) //posso anche solo rimuovere la seconda condizione per prendere gli 88 elementi (supporto == 0.2)
							{	
						//		System.out.println("il supporto " + newFP.getSupport() + " è maggiore o uguale a " + minSup);
								fpQueue.enqueue(newFP);
								outputFP.add(newFP);
						//		System.out.println("Ho aggiunto ai pattern frequenti => " + newFP.toString());
							}
							temp_min = ((Interval)item.getValue()).getSup();
						}
					}
				}
			}
		}
	//	System.out.println("Fine EXPAND \n");
		return outputFP;
	}

/**
 * Calcolo del supporto	
 * @param data tabella 
 * @param fp frequent pattern  
 * @return supporto
 */
	// Aggiorna il supporto
	static float computeSupport(Data data,FrequentPattern fp){
	//	System.out.println("INIZIO COMPUTAZIONE DEL SUPPORTO");
		int suppCount=0;
		// indice esempio
		for(int i=0;i<data.getNumberOfExamples();i++){
			//indice item
			boolean isSupporting=true;
			for(int j=0;j<fp.getPatternLength();j++)
			{
				Item item=fp.getItem(j);
				attribute attribute=item.getAttribute();
				if (item instanceof ContinuousItem)
				{
					//System.out.println(data.getAttributeValue(i, attribute.getIndex()) + " = " + data.getAttributeValue(i, attribute.getIndex()).getClass());
					//float valueInExample =  Float.valueOf(((String)data.getAttributeValue(i, attribute.getIndex()))); //si posiziona sull'attributo cercato e ne restituisce il valore
					float valueInExample;
					valueInExample = (float) data.getAttributeValue(i, attribute.getIndex());
					if(!item.checkItemCondition(valueInExample))
					{
						isSupporting=false;
						break;
					}
				}
				else
				{
					String valueInExample = (String)data.getAttributeValue(i, attribute.getIndex()); //si posiziona sull'attributo cercato e ne restituisce il valore

					if(!((DiscreteItem)item).checkItemCondition(valueInExample))
					//if(!(item.value.toString().equals(valueInExample.toString())) )
					{
						//System.out.println(item.value + " =/= " + valueInExample);
						isSupporting=false;
						break;
					}
//					else
//					{
//						System.out.println(item.value + " == " + valueInExample);
//					}
					
				}
			}
			if(isSupporting)
				suppCount++;
		}
	//	System.out.println("FINE COMPUTAZIONE");
		return ((float)suppCount)/(data.getNumberOfExamples());
	}
/**
 * Aggiunge l'item passato come argomento agli item già contenuti in un clone di FP,andando a controllare,successivamente,se tale raffinamento si potrà concretizzare.
 * @param FP Frequent Pattern	
 * @param item oggetto
 * @return Clone di fp
 */
	//aggiunge l'item passato come argomento agli item già contenuti in un clone di FP, successivamente si andrà a controllare se tale raffinamento si possa concretizzare 
	static FrequentPattern refineFrequentPattern(FrequentPattern FP, Item item)
	{
		//necessario clonare fp, altrimenti ad ogni ciclo si conservano vecchie aggiunte di cui non si dovrebbe tener conto
		FrequentPattern fpclone = new FrequentPattern();
		fpclone = (FrequentPattern)FP.clone();	
		boolean presente = false;
		//System.out.println("INIZIO REFINE");
		for(int i = 0; i < FP.getPatternLength(); i++)
		{
			if(item.getAttribute().equals(FP.getItem(i).getAttribute()) )
			{
	//			System.out.println("ATTRIBUTO " + item.toString() + " GIA' PRESENTE ");
				//questo flag evita che si entri nell'if dopo il for nonostante sia già presente (succedeva quando non avevamo il flag e solo il break)
				presente  = true;
				//salto fuori dal for risparmiando su istruzioni inutili poichè si è già constatato che l'attributo è presente 
				break;

			}
			else 
			{
				presente = false;
			}
		}
		if(presente == false)
		{
			fpclone.addItem(item);
		}
		return fpclone;
	}


	/*public static void main(String[] args) 
	{

		Data data= new Data();
		System.out.println("INVOCO DISCOVERY");
		LinkList outputFP=new LinkList();
		outputFP = frequentPatternDiscovery(data,(float)0.3);
		int i=1;
		Puntatore p=outputFP.firstList();
		while(!outputFP.endList(p))
		{
			FrequentPattern FP=(FrequentPattern)outputFP.readList(p);
			System.out.println(i+":"+FP);
			p=outputFP.succ(p);
			i++;
		}
	}*/

}



