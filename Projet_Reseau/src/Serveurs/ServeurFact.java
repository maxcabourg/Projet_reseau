package Serveurs;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
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
	
	public static void main(String argv[]){	
		try {
			port = Integer.parseInt(argv[1]);
			cache = new ArrayList();
			socket = new ServerSocket(port);
		    Socket s = socket.accept(); //Accepte la connexion TCP
			
			Scanner sc = new Scanner(s.getInputStream());
		    while (true) {
		      String msg = sc.nextLine();
		      System.out.println("Received: " + msg);
		    }//while
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
