package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.*;
import java.util.Scanner;

/**
 * Classe représentant un client envoyant un nombre a un serveur et recuperant la factorielle de celui ci
 * @author max
 *
 */
public class ClientFact extends Thread{

	/**
	 * Adresse a laquelle va se connecter le serveur
	 */
	private InetAddress adresse;
	/**
	 * Socket associé au port et a l'adresse
	 */
	private Socket socket;
	/**
	 * Nombre dont on souhaite connaitre la factorielle
	 */
	private String param;
	/**
	 * Port sur lequel le client va envoyer le nombre au serveur
	 */
	private int port;
	/**
	 * Valeur de retour
	 */
	private String retour;

	/**
	 * Fonction main du client
	 * @param args Le 1er argument correspond au nombre pour la factorielle, le 2eme au port et le 3eme a l'adresse
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

	/**
	 * Constructeur principal du Client
	 * @param param Nombre dont on souhaite connaitre la factorielle
	 * @param port Port sur lequel on va envoyer le nombre
	 * @param adresse Adresse du serveur
	 * @throws IOException En cas d'erreur de connexion
	 */
	public ClientFact(String param,int port, String adresse) throws IOException{
		super();
		this.port = port;
		this.param = param;
		this.adresse = InetAddress.getByName(adresse);
		socket = new Socket(adresse, this.port);

	}

	/**
	 * Fonction faisant marcher le client. Gere les flux d'entree sortie principalement.
	 */
	public void run(){
		try {
			//On envoie au nombre
			PrintStream output = new PrintStream(socket.getOutputStream());
			output.print(this.param+"\n");
			//Listener
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
	
	/**
	 * Getter de retour
	 * @return Renvoie la valeur de retour
	 */
	public String getRetour() {
		return retour;
	}


}
