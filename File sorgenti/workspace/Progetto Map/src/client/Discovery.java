package client;
//<applet code=\\home\\pierpaolo\\Dropbox\\PROGETTO MAP\\workspace\\Progetto Map\\bin\\client\\Discovery.class width=100 height=50>
//</applet>
import Server.*;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.awt.event.*;
import javax.swing.JApplet;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.management.PersistentMBean;
import javax.swing.*;
import Server.NoConsistentEpsException;
import java.awt.Window;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

@SuppressWarnings({ "serial", "unused" })
class NoConsistentMinSupException extends Exception{}

@SuppressWarnings("serial")
class ServerException extends Exception{}

@SuppressWarnings("serial")
class NoNameException extends Exception{}
class finestra extends WindowAdapter
{
	public void chiusura (WindowEvent e)
	{
		System.exit(0);
	}
}
/**
 *@author Stigliano-Masella
 * @version 1.1
 * <p>Company:Dipartimento di Informatica,Universita degli studi di Bari </p>
 * <p>Class Description: lancia una singola richiesta di connessione al Server e,
 *una volta avvenuta la connessione, invia e riceve messaggi tramite I/O
 *Stream, dipendentemente dalla scelta effettuata dall'utente. Attraverso un menu, l'utente
 *seleziona l'operazione, la classe invia al Server dati su minSup, epsilon, tabella di
 *database, file sul/dal quale serializzare/deserializzare . </p>
 *<p> Copyright:Copyright (c) 2017 </p>
 *
 */
@SuppressWarnings("serial")
public class Discovery extends JFrame  {

	///ATTRIBUTI////
	/**Stream di put per la comunicazione con il server*/
	private  PrintWriter out;

	/**Stream di input per la comunicazione con il server*/
	private BufferedReader in ;

	/**Bottone per selezionare la modalita "processo di scoperta" da
	 *tabella di Database*/
	private JRadioButton db =  new JRadioButton("Avvia processo di calcolo dei patterns"); 

	/**Bottone per selezionare la modalita "lettura da archivio"*/
	private JRadioButton file = new JRadioButton("Avvia lettura file"); 

////	/** Campo per inserire il nome della tabella di Database*/
////	@SuppressWarnings("unused")
//	private JTextField nameDataTxt = new JTextField(10);

	/** Bottone per avviare il processo di scoperta da tabella
	 *di Database*/
	private JButton runConstructioBt = new JButton("Esegui");

	/**Campo per inserire il valore di minSup*/
	private JTextField minSupTxt = new JTextField(3);

	/**Campo per inserire il valore di epsilon*/
	private JTextField epsTxt = new JTextField(3); 

	/**Area per visualizzare l'insieme dei pattern scoperti o letti dall'archivio*/
	private JTextArea patternsAreaTxt =  new JTextArea(10,80);

	/**Area per visualizzare messaggi, notifiche o eccezioni all'utente*/
	private JTextArea msgAreaTxt = new JTextArea(1,15); 

	/** Visualizza le tabelle disponibili per il db selezionato, permettendo la scoperta dei pattern su tale tabella */
	private JComboBox<String> tables_list  = new JComboBox<>();

	/** Visualizza i database disponibili, e permette di selezionarne uno su cui da interrogare */
	private JComboBox<String> db_list ;

	////////////   METODI  ////////////////

	/**Cattura l'evento di pressione del bottone runConstructioBt e avvia la esecuzione dipendentemente
	 *dalla modalita selezionata dall'utente (JRadioButton db- JRadioButton file) . Se e' selezionata la
	 *voce "Discovery patterns from db" avvia il processo inviando i parametri letti dai TextField e il nome
	 *della tabella del db inserita. Nel caso e' selezionata la voce "Reading patterns from file" verra
	 *inviato il nome dell'archivio da de-serializzare. Alla terminazione del processo i patterns saranno
	 *serializzate su Server e visualizzate su Applet nella area patternsAreaTxt. L'esito del processo
	 *potra essere visualizzato su msgAreaTxt
	 *Si occupera anche di verificare la correttezza dei dati minSup ed epsilon inseriti visualizzandone successo o fallimento tramite finestre JDialog
	 *@param e evento avvenuto
	 */
	private void startDiscoveryBt_mouseClicked(ActionEvent e)
	{
		Socket socket = null; 
		try{
			InetAddress addr;
			addr = InetAddress.getByName("127.0.1.1");
			socket = new Socket(addr, MultiServer.PORT);
			in = new BufferedReader(	new InputStreamReader(socket.getInputStream())); //cio che ci fa ricevere dal server
			// Flush automatico con PrintWriter:
			out = new PrintWriter( new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true); //cio che ci fa mandare al server	
			////CONNESSIONE CON IL SERVER FISSATA////
			out.println(db_list.getSelectedItem()); //invio il nome del db da selezionare
			System.out.println(db_list.getSelectedItem());
			out.println(tables_list.getSelectedItem()); //invio il nome della tabella su cui lavorare
			System.out.println(tables_list.getSelectedItem());
			float supporto = Float.parseFloat(minSupTxt.getText()); //trasformo in float supporto preso dalla gui
			if(supporto < 0 || supporto > 1 || minSupTxt.getText().isEmpty())
				throw new NoConsistentMinSupException();
			float epsilon = Float.parseFloat(epsTxt.getText());
			if (epsilon > 1 || epsilon < 0 || epsTxt.getText().isEmpty() )
				throw new NoConsistentEpsException();
			out.println(supporto);//invio il supporto al server
			patternsAreaTxt.append("\n **** PATTERN FREQUENTI **** \n");
			String line;
			while(!(line = in.readLine()).equals("Stop"))
			{
				patternsAreaTxt.append(line + "\n"); //INSERISCO OGNI FREQUENTE NELL'AREA DI TESTO
			}
			out.println(epsilon); //invio epsilon al server
			patternsAreaTxt.append("\n\n***** PATTERN CHIUSI E RISPETTIVI RIDONDANTI IN ELABORAZIONE. *****\n");
			String linea_archivioN;
			//visualizzo riga per riga l'archio ricevuto dal server
			while (!(linea_archivioN = in.readLine()).equals("Stop"))
			{
				patternsAreaTxt.append(linea_archivioN + "\n");
			}

			patternsAreaTxt.append("\n***** PATTERN CHIUSI E RISPETTIVI RIDONDANTI ELABORATI. *****\n\n");
			JOptionPane avviso =  new JOptionPane("Nome del file per il salvataggio ");
			avviso.add(msgAreaTxt);
			JDialog insert = avviso.createDialog("Salvataggio dell'archivio");
			insert.setVisible(true);
			String save=msgAreaTxt.getText();
			out.println(save);
			//caso in cui il salvataggio non sia andato a buon fine 
			if(in.readLine().equals("&"))
			{
				JOptionPane eccezione =  new JOptionPane("Salvataggio non riuscito", JOptionPane.ERROR_MESSAGE);
				JDialog exc = eccezione.createDialog("ERRORE" );
				exc.setVisible(true);
			}
		}
		//crea un popup di errore da far visualizzare all'utente
		catch (IOException e1) 
		{
			JOptionPane eccezione =  new JOptionPane("Comunicazione con il server non riuscita", JOptionPane.ERROR_MESSAGE);
			JDialog exc = eccezione.createDialog("ERRORE" );
			exc.setVisible(true);
		}

		catch (NoConsistentMinSupException n)
		{
			//popup di errore
			JOptionPane eccezione =  new JOptionPane("Il supporto minimo deve essere compreso tra 0 e 1", JOptionPane.ERROR_MESSAGE);
			JDialog exc = eccezione.createDialog("ERRORE" );
			exc.setVisible(true);
		}

		catch (NoConsistentEpsException no)
		{
			//popup di errore
			JOptionPane eccezione =  new JOptionPane("Epsilon deve essere compreso tra 0 e 1", JOptionPane.ERROR_MESSAGE);
			JDialog exc = eccezione.createDialog("ERRORE" );
			exc.setVisible(true);
		}
		catch (NumberFormatException em)
		{
			//popup di errore
			JOptionPane eccezione =  new JOptionPane("Parametro/i per il calcolo non valido/i", JOptionPane.ERROR_MESSAGE);
			JDialog exc = eccezione.createDialog("ERRORE" );
			exc.setVisible(true);
		}
		finally {
			System.out.println("closing...");
			try {
				socket.close();

			} 
			catch (IOException ioo)
			{
				// TODO Auto-generated catch block
				ioo.printStackTrace();
				System.err.println("ALCUNI ERRORI NON HANNO PERMESSO DI APIRE IL SERVER CORRETTAMENTE, DUNQUE LA CHIUSURA NON E' STATA EFFETTUATA");
			}
		}
	}
	/**
	 * Cattura l'evento di pressione su uno dei db mostrati nella combo box, facendo in modo che vengano mostrate di conseguenza le giuste tabelle corrispondendti
	 * ad ogni db.
	 * All'inizio, e' visualizzato un db di default (il primo inserito nel programma) ma nessuna tabella e' visibile fino a che non vi e' un click sul db stesso.
	 * @param e evento di click sulla combo box
	 * @param j pannello da aggiornare
	 */
	private void change_db (ActionEvent e, JPanel j )
	{
		if(db_list.getSelectedItem().toString().equals("cfp17_matricola"))
		{
			tables_list.setEditable(true);
			tables_list.removeAllItems();
			tables_list.addItem("iris");
			tables_list.addItem("missingplay");
			tables_list.addItem("playtennis");
		}
		if(db_list.getSelectedItem().equals("estensionedb"))
		{
			tables_list.setEditable(true);
			tables_list.removeAllItems();
			tables_list.addItem("player");
			tables_list.addItem("capocannoniere");
		}
	}
	/**
	 * Cattura l'evento di scelta di caricamento file, che aprira una finestra popup per l'inserimento del nome del file che si intende caricare
	 * @param e evento
	 */
	private void loadData (ActionEvent e)
	{
		JOptionPane avviso =  new JOptionPane("Nome del file");
		//msgAreaTxt.setBackground(SystemColor.red);
		avviso.add(msgAreaTxt);
		JDialog insert = avviso.createDialog("Caricamento file");
		insert.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		insert.setVisible(true);
		Socket socket = null; 
		try
		{			
			InetAddress addr;
			addr = InetAddress.getByName("127.0.1.1");
			socket = new Socket(addr, MultiServer.PORT);
			in = new BufferedReader(	new InputStreamReader(socket.getInputStream())); //cio che ci fa ricevere dal server
			// Flush automatico con PrintWriter:
			out = new PrintWriter( new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true); //cio che ci fa mandare al server	
			////CONNESSIONE CON IL SERVER FISSATA////
			out.println("s"); //invio del carattere per avviare il rispettivo caso nel server

			if (msgAreaTxt.getText().isEmpty())
				throw new NoNameException();

			out.println(msgAreaTxt.getText()); //prendiamo cosa e' stato scritto nella box
			String linea_archivio;
			//visualizzo riga per riga l'archio ricevuto dal server
			while (!(linea_archivio = in.readLine()).equals("Stop"))
			{
				if(linea_archivio.equals("$"))
					throw new ServerException();
				patternsAreaTxt.append(linea_archivio + "\n"); //scrivo nell'area di output tutto l'archivio riga per riga
			}
			patternsAreaTxt.append("\n*** CARICAMENTO DELL'ARCHIVIO COMPLETATO ***\n");
			db.setSelected(true);
		}
		catch (ServerException z)
		{
			JOptionPane eccezione =  new JOptionPane("File non trovato!", JOptionPane.ERROR_MESSAGE);
			JDialog exc = eccezione.createDialog("ERRORE" );
			exc.setVisible(true);
			db.setSelected(true);
		} 

		//crea un popup di errore da far visualizzare all'utente
		catch (IOException e1) 
		{
			JOptionPane eccezione =  new JOptionPane("Comunicazione con il server non riuscita", JOptionPane.ERROR_MESSAGE);
			JDialog exc = eccezione.createDialog("ERRORE" );
			exc.setVisible(true);
			db.setSelected(true);
		} 
		//non e' stato inserito il nome del file da caricare
		catch (NoNameException e1) 
		{
			JOptionPane eccezione =  new JOptionPane("Il nome del file non puo essere vuoto", JOptionPane.ERROR_MESSAGE);
			JDialog exc = eccezione.createDialog("ERRORE" );
			exc.setVisible(true);
			db.setSelected(true);
		}
		////chiusura del socket in qualunque caso
		finally {
			System.out.println("closing...");
			try {
				socket.close();
			} 
			catch (IOException ioo)
			{
				// TODO Auto-generated catch block
				ioo.printStackTrace();
				System.err.println("ALCUNI ERRORI NON HANNO PERMESSO DI APIRE IL SERVER CORRETTAMENTE, DUNQUE LA CHIUSURA NON E' STATA EFFETTUATA");
			}
		}

	}
	/**
	 *Costruttore. Istanzia un contenitore generico a cui vengono aggiunti JPanel.
	 *Ad ogni JPanel vengono aggiunti i button, le label le caselle di testo secondo uno	specifico Layout.
	 */
	public  Discovery ()
	{

		///INIZIALIZZO INTERFACCIA 
		Container cp = getContentPane(); //contenitore generico a cui vanno aggiunti i jpanel
		cp.setLayout(new FlowLayout());
		JPanel cpDiscoveryMining=new JPanel();
		Border borderTree = BorderFactory.createTitledBorder("Operazione da effettuare");
		cpDiscoveryMining.setBorder(borderTree);
		cpDiscoveryMining.setLayout(new BoxLayout (cpDiscoveryMining,BoxLayout.Y_AXIS));
		ButtonGroup group=new ButtonGroup();
		db=new JRadioButton("Scoperta di pattern");
		file =new JRadioButton("Carica pattern gia calcolati");
		group.add(db);
		group.add(file);
		db.setSelected(true); //permette di avere un solo button cliccato per volta
		//listener per il bottono file, che avvia il caricamento di un eventuale file con pattern gia elaborati
		file.addActionListener(new java.awt.event.ActionListener() {  public void actionPerformed(ActionEvent e) {loadData(e);} });
		cpDiscoveryMining.add(db);
		cpDiscoveryMining.add(file);
		cp.add(cpDiscoveryMining);
		JPanel insert_data = new JPanel();
		Border border_data = BorderFactory.createTitledBorder("Parametri per il calcolo");
		insert_data.setBorder(border_data);
		String[] dbs = {"cfp17_matricola", "estensionedb"};
		db_list = new JComboBox<>(dbs); //database su cui si vuole lavorare
		db_list.addActionListener(new java.awt.event.ActionListener() {  public void actionPerformed(ActionEvent e) {change_db(e , insert_data);} });
		insert_data.add(db_list);
		insert_data.add(new Label("Tabella"));
		insert_data.add(tables_list);
		insert_data.add(new Label("supporto minimo"));
		insert_data.add(minSupTxt); //zona di testo per inserire il minsup
		insert_data.add(new Label("epsilon"));
		insert_data.add(epsTxt); //zona di testo per inserire epsilon
		cp.add(insert_data); //aggiungo le interfacce appena create
		runConstructioBt.addActionListener(new java.awt.event.ActionListener() {  public void actionPerformed(ActionEvent e) {startDiscoveryBt_mouseClicked(e);} });
		cp.add(runConstructioBt);
		JPanel risultato = new JPanel();
		risultato.setLayout(new GridLayout(1, 2));
		Border border_result = BorderFactory.createTitledBorder("Risultati");
		risultato.setBorder(border_result);
		JScrollPane scroll =  new JScrollPane(patternsAreaTxt); //area per i pattern
		risultato.add(scroll);
		patternsAreaTxt.setEditable(false);
		cp.add(risultato);
		cp.setBackground(SystemColor.LIGHT_GRAY);
		this.setVisible(true);
		this.setSize(1000,400);
	}
}
