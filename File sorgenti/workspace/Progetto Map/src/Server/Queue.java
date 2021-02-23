//classe fornita dal professore
package Server;

@SuppressWarnings("serial")
class EmptyQueueException extends RuntimeException{} //eccezione sollevata in caso di coda vuota
/**
 * 
 * @author Prof.Loglisci
 * @version 1.0
 * <p>Company:Dipartimento di Informatica,Università degli studi di Bari </p>
 * <p>Class Description:Modella una struttura che contiene in modo FIFO i pattern frequenti a livello k da usare per generare i patter frequenti a livello k+1 </p>
 *<p> Copyright:Copyright (c) 2017 </p>
 */
//classe Queue generica, che viene parametrizzata a seconda di ciò che andra ad ospitare
public class Queue <T> {

	private Record begin = null;

	private Record end = null;
	//nested class
	private class Record {

		public Object elem;

		public Record next;

		public Record(Object e) {
			this.elem = e; 
			this.next = null;
		}
	}


	public boolean isEmpty() {
		return this.begin == null;
	}	

	//inserisci in coda
	public void enqueue(Object e) {
		if (this.isEmpty())
			this.begin = this.end = new Record(e);
		else {
			//prendo l'ultimo oggetto, prendo il suo successivo e gli assegno un nuovo record,
			//a suo volta contenente il nuovo elemento in input e una locazione next che punterà al successivo record
			this.end.next = new Record(e);
			this.end = this.end.next;
		}
	}

	//primo elemento della coda
	public Object first(){
		return this.begin.elem;
	}

	//rimuovi dalla coda
	public void dequeue(){
		try
		{
			if(this.begin==this.end)
			{
				if(this.isEmpty())
				{
					throw new EmptyQueueException();

				}
				else
				{
					this.begin=this.end=null;
				}


			}
			else
			{
				begin=begin.next;
			}
		}
		//eccezione in caso di pila vuota
		catch (EmptyQueueException e)
		{
			System.out.println("The queue is empty!");
		}


	}
	
	//TEST DELLA ECCEZIONE
	public static void main(String[] args) throws EmptyQueueException
	{
		Queue<String> coda = new Queue<String>();
		coda.dequeue();
		
	}


}

