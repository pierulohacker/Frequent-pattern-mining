package Server;
import java.util.LinkedList;
/**
 * Interfaccia che gestisce permutazioni di itmeset e itemset di lunghezza 1
 * @author Masella-Stigliano
 *
 */
public interface Irrilevancy 
{
	/**
	 * Gestisce permutazioni di itemset in una lista di pattern frequenti
	 * @param outFP lista di pattern frequenti ottenuta mediante il processo di mining      //NON UTILIZZATO POICHE' LA GESTIONE DELLE PERMUTAZIONI AVVIENE NELL'EQUALS DEI PATTERN FREQUENTI
	 */
	void prunePermutations (LinkedList<FrequentPattern> outFP);

	/**Gestisce itemset di lunghezza 1 in una lista di pattern frequenti, eliminandoli.
	 * 
	 * @param outFP lista di pattern frequenti ottenuta mediante il processo di mining
	 */
	 void pruneSingle (LinkedList<FrequentPattern> outFP);

}
