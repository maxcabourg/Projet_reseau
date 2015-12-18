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
	 * Indique le nombre de clients qui a été crée;
	 */
	private static int nombreClients;
	/*
	 * Valeur dont on souhait connaitre la factorielle
	 */
	private static int valeurACalculer;
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
		nombreClients++; //Si c'est le 1er client que l'on crée, on demande la valeur souhaitée, sinon on envoie une requete avec la valeurACalculer - 1
		if(nombreClients == 1)
		{
			ClientFact client = new ClientFact(argv);
			try {
				PrintStream output = new PrintStream(socket.getOutputStream());
				System.out.print("Entrez le nombre dont vous desirez connaitre la factorielle : ");
				Scanner sc = new Scanner(System.in);
			    String msg;
			    while (true) {
			    	valeurACalculer = sc.nextInt();
			        output.println(Integer.toString(valeurACalculer));
			      }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else; //TODO
	    
			
	}

}
