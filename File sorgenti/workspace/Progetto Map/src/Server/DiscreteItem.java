package Server;

/**
 * 
 * @author Masella-Stigliano
 * @version 1.0
 * <p>Company:Dipartimento di Informatica,Università degli studi di Bari </p>
 * <p>Class Description:Estende la classe Item e rappresenta una coppia : Attributo discreto-valore discreto (Es:Outlook="Sunny"). </p>
 *<p> Copyright:Copyright (c) 2017 </p>
 */
public class DiscreteItem extends Item {

	private static final long serialVersionUID = 1L;

	/**
	 * Invoca il supercostruttore (della superclasse Item)
	 * (richiesto dalla serializzazione, poichè essa necessita che vi sia esplicitamente un costruttore visibile,
	 * anche se non effettua operazioni)
	 * @param attributo attributo discreto
	 * @param value valore dell'attributo discreto
	 */
	DiscreteItem(DiscreteAttribute attributo, String value) {
		super(attributo, value);

	}


	@Override
	/**Controlla un valore fornito in input con il valore dell'attributo discreto dell'item discreto chiamante
	 * @param value valore discreto
	 * @return true se il valore in input è uguale al valore dell'attributo discreto dell'item discreto chiamante, false altrimenti
	 */
	boolean checkItemCondition(Object value) {
		return (value.toString().equals(this.getValue().toString()));
	}
	
	//override
	/**
	 * Confronta due item
	 * @param it item da confrontare con l'item chiamante
	 * @return true se gli item sono uguali (hanno stesso attributo e stesso valore), false altrimenti
	 */
	public boolean equals(Item it)
	{
		if(this.getAttribute().equals(it.getAttribute()) && this.checkItemCondition(it.getValue()))
		{
			return true;
		}
		else
			return false;
	}



}
