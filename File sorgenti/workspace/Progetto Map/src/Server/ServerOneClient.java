package Server;
import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.LinkedList;
/**
 * <p> Class description:Comunica con un solo Client procedendo nell'uso dell'applicazione dipendentemente dalla sezione di input dal Client.</p>
 * @author Stigliano-Masella
 * @version 1.0
 * <p>Company:Dipartimento di Informatica,Università degli studi di Bari </p>
 * <p> Copyright:Copyright (c) 2017 </p>
 */
class ServerOneClient extends Thread {
	/**Socket per la connessione al server*/
	private Socket socket;

	/**Riceve dal server*/
	private BufferedReader in;

	/**Invia al server*/
	private PrintWriter out;

	/**
	 * Instaura la connessione fra client e server tramite il socket in input
	 * @param s Socket del client
	 * @throws IOException Problemi con lo stream.
	 */
	public  ServerOneClient (Socket s) throws IOException
	{
		socket = s;
		in = new BufferedReader( new InputStreamReader(	socket.getInputStream()));

		out = new PrintWriter(
				new BufferedWriter(
						new OutputStreamWriter(
								socket.getOutputStream())), true);
		// se una qualsiasi delle chiamate precedenti solleva una
		// eccezione, il processo chiamante è responsabile della 
		// chiusura del socket. Altrimenti lo chiuderà il thread 
		start(); // Chiama run()
	}
	/**
	 * Gestisce le richieste del Client eseguendo una delle possibili selezioni dell'utente(scoperta di closed pattern,salvataggio su file e caricamento da file).
	 */
	public void run() {
		{
			try
			{
				String input;
				if (( input = in.readLine()).equals("s"))
				{ 
					String up =in.readLine();
					String path = null; //carica dal path in cui si trova l'eseguibile
					String file = up + ".txt"; //DA METTERE NEL SERVER
					ClosedPatternArchive archivio = (ClosedPatternArchive)ClosedPatternArchive.load(path,file);  
					System.out.println(archivio); //stampo sul server l'archivio caricato, per controllare la correttezza
					out.println(archivio);//invio l'archivio al client
					out.println("Stop");//necessario per far capire al client che siamo arrivati alla fine dell'archivio.
				}
				else
				{
					System.out.println("Sono nel caso n");
					String db=input ;
					String tabella = in.readLine();
					System.out.println(db);
					System.out.println(tabella);
					Data data= new Data(tabella,db); //solleva un eccezione MySQLSyntaxErrorException
					String supportstring= in.readLine();
					float support=Float.parseFloat(supportstring);
					//System.out.print("Supporto nel server" + support);
					//avvio la scoperta dei Frequent Pattern.
					LinkList outputFP = new LinkList();
					LinkedList<FrequentPattern> fpToClose = new LinkedList<FrequentPattern>();
					outputFP = FrequentPatternMiner.frequentPatternDiscovery(data,support); //può sollevare eccezione di EmptySetException a causa di tabelle del DB vuote
					int i=1;
					Puntatore p=outputFP.firstList();
					System.out.println("***** PATTERN FREQUENTI *****");
					//linked list da usare per il calcolo dei closed, che conterrà i frequent pattern calcolati
					while(!outputFP.endList(p))
					{
						FrequentPattern FP=(FrequentPattern)outputFP.readList(p);
						fpToClose.add(FP);
						out.println(i+":"+FP); //invio al client un pattern frequente
						System.out.println(i+":"+FP); //stampo sul server la riga di pattern frequente, per controllare la correttezza
						p=outputFP.succ(p);
						i++;
					}
					out.println("Stop");

					String nepsilon=in.readLine();
					float epsilon=Float.parseFloat(nepsilon);		
					ClosedPatternArchive archiveout = ClosedPatternMiner.closedPatternDiscovery(fpToClose, epsilon);
					System.out.print(archiveout);
					out.println(archiveout);
					out.println("Stop");
					try
					{
						String save=in.readLine();
						String path = null; //salva nel path in cui si trova l'eseguibile
						String file = save +".txt";
						//salvo l'archivio
						archiveout.save(path,file);
						System.out.println(save);
						out.println("*** ARCHIVIO SALVATO ***");
						socket.close();
						System.out.print("Closing Single Server...");
					}
					catch (IOException e)
					{
						out.println("&");
					}
				}
			}

			catch (EmptySetException e)
			{
				out.println("La tabella inserita non ha transazioni");
			}
			catch (IOException e)
			{
				out.println("$");
				System.out.print("Sto mandando il dollaro");
			}
			catch (DatabaseConnectionException e1) {
				out.println("Creazione dell'archivio non riuscita a causa di problemi di comunicazione con il DB ");
				e1.printStackTrace();
			} catch (SQLException e1) {
				out.println("Errore nella esecuzione di una o più query, o comandi al DB");
				e1.printStackTrace();
			} catch (NoValueException e1) {
				out.println("Uno o più valori nel db non possono essere elaborati poichè nullo/i");
				e1.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();}



		}
		try
		{
			socket.close();

		} 
		catch(IOException e)
		{
			System.err.println("Socket not closed");
		}


	}


}


