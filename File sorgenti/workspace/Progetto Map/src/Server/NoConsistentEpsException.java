package Server;

@SuppressWarnings("serial")
/**
 * per gestire il caso di inserimento da tastiera di un valore di epsilon non incluso nelrange [0,1]. 
 * L'eccezione viene sollevata e gestita in MainTest
 */
public class NoConsistentEpsException extends Exception {}
