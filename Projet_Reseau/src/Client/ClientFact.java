package Client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
/*
 * ClientFact est une classe representant un client capable de se connecter à un serveur effectuant le calcul d'une factorielle.
 * @author Max Cabourg
 */
public class ClientFact {
	
	/*
	 * Socket du client indiquant sur qui il va envoyer la demande
	 */
	private static Socket socket;
	/*
	 * Adresse du serveur destinataire
	 */
	private static InetAddress adresse;
	/*
	 * Adresse du port
	 */
	private static int port;
	
	/*
	 * Constructeur de la classe ClientFact
	 * @param argv
	 * Parametres de la ligne de commande : l'ip du serveur puis son port
	 */
	public ClientFact(String[] argv){
		try {
			adresse = InetAddress.getByName(argv[0]); //Recupere l'adresse ip
			port = Integer.parseInt(argv[1]); //Recupere le port
			socket = new Socket(adresse, port);	
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] argv){	
		ClientFact client = new ClientFact(argv);
		try {
			PrintStream output = new PrintStream(socket.getOutputStream());
			Scanner sc = new Scanner(System.in);
		    String msg;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
			
	}

}
