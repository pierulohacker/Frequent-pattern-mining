package Server;

import java.io.*;
import java.net.*;

/**
 * <p> Class description: Accetta la richiesta di ciascun Client e le evade avviando un thread ServerOneClient.</p>
 * @author Stigliano-Masella
 * @version 1.0
 * <p>Company:Dipartimento di Informatica,Università degli studi di Bari </p>
 * <p> Copyright:Copyright (c) 2017 </p>
 */
public class MultiServer {

	/**Porta per la connessione al server*/
	public static final int PORT = 8194;

	/**Indica il numero di client che si sono connessi al server*/
	static int clients=0;


	public static void main(String[] args) throws IOException {


		ServerSocket s = new ServerSocket(PORT);
		System.out.println("Server Started");
		try {
			while(true) {
				// Si blocca finchè non si verifica una connessione:
				Socket socket = s.accept();
				System.out.println("client: "+(++clients));
				try {
					new ServerOneClient(socket);
				} 
				catch(IOException e) {
					// Se fallisce chiude il socket,
					// altrimenti il thread la chiuderà:
					socket.close();
				}
			}
		} finally {
			s.close();
		}
	}
}

