package Server;


/**
 * 
 * @author Stigliano-Masella
 * @version 1.0
 * <p>Company:Dipartimento di Informatica,Università degli studi di Bari </p>
 * <p>Class Description:Estende la classe Item e rappresenta una coppia : Attributo continuo-intervallo di valori assunti 
 * (Es:Temperature=[10;30.3]) </p>
 *<p> Copyright:Copyright (c) 2017 </p>
 */
public class ContinuousItem extends Item 
{
	/**Estremo inferiore dell'intervallo */
	private float inf;

	/** Estremo superiore dell'intervallo */
	private float sup;

	/**
	 * ID funzionale alla serializzazione
	 */
	private static final long serialVersionUID = 1L;

	//Costruttore
	/** 
	 * Invoca il costruttore della superclasse Item e avvalora i membri
	 * @param attribute attributo continuo che avvalora attribute della superclasse item
	 * @param value Intervallo con cui avvalora l'attributo value della superclasse item
	 */
	public ContinuousItem (ContinuousAttribute attribute,Interval value)
	{
		super(attribute, value);
		inf = value.getInf();
		sup = value.getSup();
	}
	//Metodi//
	/**
	 * Verifica che il valore-intervallo in input è incluso nell'intervallo di valori associato all'item
	 * (se maggiore o uguale a inf e minore o uguale a sup)
	 * @param value valore di cui controllare l'appartenenza all'intervallo del Contuous item 
	 * @return true se value appartiene all'intervallo del ContinuousItem, false altrimenti
	 */
	boolean checkItemCondition (Object value)
	{
		if(value instanceof Interval)
			return (((Interval)value).getInf() >= inf && ((Interval)value).getSup() <= sup);
		else
			return ((float)value >= inf &&  (float)value <= sup);

	}
	/**Confronta l'item continuo chiamante con un item in input: se l'item in input non è continuo, l'uguaglianza è già non verificata;
	 * altrimenti, controllo che gli attributi e gli estremi siano gli stessi
	 * @param it item in input, di cui non si conosce il tipo  a priori
	 * @return true se l'item chiamato e l'item in input sono uguali, false altrimenti
	 */
	public boolean equals(Item it) 
	{
		if (it.getClass() != this.getClass()) //per capire se innanzitutto siano entrambi continui
			return false;
		else
		{
			//controllo che gli attributi siano uguali, e poi che gli estremi degli intervalli siano gli stessi
			if(this.getAttribute().equals(it.getAttribute()) && ((Interval)it.getValue()).getInf() == ((Interval)this.value).getInf()  && ((Interval)it.getValue()).getSup() == ((Interval)this.value).getSup())
			{
				return true;
			}
			else
				return false;
		}
	}

	/**
	 * Concatena in un oggetto String l'item continuo nella forma Attributo continuo, intervallo
	 * @return Item continuo in formato stringa (Es. Temperatura [10.2;20.4])
	 */
	public String toString()
	{
		String output;
		output = this.attributo.toString()  + " [" + ((Interval)this.value).getInf() + "," + ((Interval)this.value).getSup() + "]"; 
		return output;

	}

	//tester per la classe
	public static void main(String[] args)
	{
		ContinuousAttribute attr_cont1 = new ContinuousAttribute("temperatura", 0, (float)0.0, (float)3.2);
		ContinuousAttribute attr_cont2 = new ContinuousAttribute("temperatura", 0, (float)1.0, (float)4.2);
		Interval interv1 = new Interval((float)0.0, (float)3.2);
		Interval interv2 = new Interval((float)1.0, (float)4.2);
		ContinuousItem item1 = new ContinuousItem(attr_cont1, interv1);
		ContinuousItem item2 = new ContinuousItem(attr_cont2, interv2);
		System.out.println("I due item sono: " + item1 +" e " + item2);
		System.out.println("TESTIAMO CHECK...");
		System.out.println(item1.checkItemCondition(item2.value));
		System.out.println(interv2.getInf() >= interv1.getInf() && interv2.getSup() <= interv1.getSup() );
	}

}

