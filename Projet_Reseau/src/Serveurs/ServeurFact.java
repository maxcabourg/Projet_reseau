package Serveurs;

import java.io.PrintStream;
import java.net.*;
import java.util.*;

import Client.ClientFact;

/**
 * Classe representant un serveur capable de calculer la factorielle d'un nombre.
 * Le serveur se charge de creer des sous clients qui vont a leur tour se connecter au serveur
 * @author max
 *
 */
public class ServeurFact extends Thread{

	

	/**
	 * Port sur lequel on écoute
	 */
	private int port;
	/**
	 * Cache du serveur qui va aller fouiller si on a pas deja calculé la factorielle du nombre pour gagner du temps
	 */
	private HashMap<Integer,Integer> cache;
	
	/**
	 * Fonction main du serveur. Le programme part de là.
	 * @param args 1er argument : Le port sur lequel écouter
	 */
	public static void main(String[] args) {
		ServeurFact server = new ServeurFact(Integer.parseInt(args[0]));
		server.start();
	}

	/**
	 * Classe interne qui va se charger de gerer un client qui se connecte au serveur.
	 * Elle stockera notemment le flux d'entree et de sortie de ce client
	 * @author max
	 *
	 */
	private class ClientThread extends Thread{
		
		/**
		 * Port du client
		 */
		private int port;
		/**
		 * Socket du client
		 */
		private Socket socket;
		/**
		 * Nombre associé a ce client
		 */
		private int param;
		/**
		 * Flux de sortie du client
		 */
		private PrintStream sortie;
		/**
		 * Cache du serveur qui est en fait partagé par tous les clients
		 */
		private HashMap<Integer,Integer> cache;


		/**
		 * Constructeur de clientThread
		 * @param socket Socket que l'on va associer a ce thread
		 * @param cache Cache du serveur partagé entre chaque clientThread
		 * @param port Port du client
		 * @throws Exception La connextion peut echouer
		 */
		public ClientThread(Socket socket,HashMap<Integer,Integer> cache,int port) throws Exception{
			super();
			this.socket = socket;
			this.port=port;
			Scanner entree = new Scanner(socket.getInputStream());
			this.param = Integer.parseInt(entree.next());
			this.sortie = new PrintStream(socket.getOutputStream());
			this.cache=cache;

		}

		/**
		 * Fonction déclenchant le traitement du client.
		 * Elle s'occupera notamment de creer les sous clients.
		 */
		public void run(){
			try{	
				if(this.cache.containsKey(param))//On a deja effectue le calcul
					System.out.println("Factorielle de "+param+" vaut : "+this.cache.get(param));
				else
				{
					if(param == 0)
						this.cache.put(0,1);	
					else
					{
						ClientFact c = new ClientFact(Integer.toString(param-1),this.port,"localhost");
						c.start();
						while(c.getRetour()==null);
						this.cache.put(param,param*Integer.parseInt(c.getRetour()));		
					}
				}
				//Envoie au client et fermeture de la connexion
				sortie.print(Integer.toString(this.cache.get(param))+"\n");
				socket.close();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}


	/**
	 * Constructeur du serveur
	 * @param port Port sur lequel il va ecouter
	 */
	public ServeurFact(int port){
		super();
		this.port = port;
		cache = new HashMap<Integer,Integer>();

	}


	/**
	 * Fonction faisant tourner le serveur.
	 * Il s'attache a creer un client Thread a chaque fois qu'un client se connecte
	 */
	public void run(){
		try{
			ServerSocket serverSocket = new ServerSocket(port);
			while(true)
			{
				Socket socket = serverSocket.accept();
				ClientThread clientThread = new ClientThread(socket, cache, port);
				clientThread.start();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
