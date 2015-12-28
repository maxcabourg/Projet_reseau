package Serveurs;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import Client.ClientFact;
import Threads.ThreadFact;
/*
 * Classe representant un serveur capable de calculer une factorielle
 * @author Max Cabourg
 */
public class ServeurFact {
	
	/*
	 * Port sur lequel ecoute le serveur
	 */
	private static int port;
	/*
	 * Le cache contiendra les calculs déjà fait précédemment durant l'execution du serveur
	 */
	private static ArrayList cache;
	/*
	 * Le serveur en lui meme
	 */
	private static ServerSocket socket;
	/*
	 * Valeur a calculer
	 */
	private static int valeurInitiale;
	/*
	 * 
	 */
	private static ThreadFact tf;
	private static int nbRequetes = 0;
	
	public static void main(String argv[]){	
		try {
			nbRequetes++;
			if(nbRequetes == 1) //Initialisation du socket sur le 1er appel
			{
				port = Integer.parseInt(argv[0]); //Recupere le port a partir de la ligne de commande
				cache = new ArrayList();
				socket = new ServerSocket(port); //Le socket va ecouter le port passé en parametre
				Socket s = socket.accept(); //Accepte la connexion TCP			
				Scanner sc = new Scanner(s.getInputStream()); //Recupere le nombre envoye par le client
				while (true) {
				      String msg = sc.nextLine();
				      System.out.println("Received: " + msg);
				      valeurInitiale = Integer.parseInt(msg);
				}//while
			}
			else
			{
				ClientFact client = new ClientFact(argv, valeurInitiale - nbRequetes); //Creation du nouveau client qui va calculer fact(n-1)
			}
		        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
