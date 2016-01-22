package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.*;
import java.util.Scanner;

public class ClientFact extends Thread{


	private InetAddress adresse;
	private Socket socket;
	private String param;
	private int port;
	private String retour;

	/*Client de la fonction de calcul Factorielle 
	 *	Paramètres:
	 * 1, le paramètre de la fonction Factorielle
	 * 2, le port du serveur
	 * 3, l'adresse du serveur
	 */
	public static void main(String[] args) {
		//Création du client
		ClientFact client;
		try {
			client = new ClientFact(args[0],Integer.parseInt(args[1]),args[2]);
			client.run();
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ClientFact(String param,int port, String adresse) throws IOException{
		super();
		this.port = port;
		this.param = param;
		this.adresse = InetAddress.getByName(adresse);
		socket = new Socket(adresse, this.port);

	}

	public void run(){
		try {
			//Connection au Serveur
			//Envoie du paramètre
			PrintStream output = new PrintStream(socket.getOutputStream());
			output.print(this.param+"\n");
			//Attente de la réponse du serveur
			InputStream input = socket.getInputStream();
			Scanner sc = new Scanner(input);
			String msg;
			if(sc.hasNext()){
				msg = sc.nextLine();
				System.out.println("Resultat: "+msg);
				this.retour=msg;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getRetour() {
		return retour;
	}


}
