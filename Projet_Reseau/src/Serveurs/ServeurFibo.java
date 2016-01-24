package Serveurs;

import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.util.*;

import Client.ClientFibo;

/**
 * Classe representant un serveur capable de calculer la suite de Fibonacci d'un terme en créant des sous clients.
 * @author max
 *
 */
public class ServeurFibo extends Thread{

	/**
	 * Fonction principale du serveur. Le démarre.
	 * @param args Un seul argument : le port sur lequel écouter
	 */
	public static void main(String[] args) {
		ServeurFibo serveur1 = new ServeurFibo(Integer.parseInt(args[0]),1);
		serveur1.start();
		ServeurFibo serveur2 = new ServeurFibo(Integer.parseInt(args[0])+1,2);
		serveur2.start();
		serveur1.setServeur2(serveur2);
		serveur2.setServeur2(serveur1);
	}

	private int numero;
	private ServeurFibo serveur2;
	private int port;
	private HashMap<Integer,Integer> cache;

	/**
	 * Classe interne représentant un client.
	 * @author max
	 *
	 */
	private class ClientThread extends Thread{

		/**
		 * Port du client
		 */
		private int port;
		/**
		 * Socket associé à ce client
		 */
		private Socket socket;
		/**
		 * Nombre dont on veut obtenir Fibonacci
		 */
		private int param;
		/**
		 * Flux de sortie du client
		 */
		private PrintStream sortie;
		/**
		 * Cache du client
		 */
		private HashMap<Integer,Integer> cache;

		
		/**
		 * Constructeur du client
		 * @param socket Socket a attribuer à ce client
		 * @param cache Cache du client
		 * @param port Port du client
		 * @throws Exception quand la connexion échoue
		 */
		public ClientThread(Socket socket,HashMap<Integer,Integer> cache,int port) throws Exception{
			super();
			this.socket = socket;
			this.port=port;
			Scanner input = new Scanner(socket.getInputStream());
			param = Integer.parseInt(input.next());
			sortie = new PrintStream(socket.getOutputStream());
			this.cache=cache;

		}

		/**
		 * Fonction principale pour traiter le client
		 */
		public void run(){
			try
			{
				if(this.cache.containsKey(param))
					System.out.println("Fibonacci de "+param+" = "+cache.get(param));
				else
				{
					if(param == 0 || param==1)
						this.cache.put(param,param);	
					else
					{
						if(numero == 1)
							creerClient1();
						else if(numero == 2)
							creerClient2();
					}			
				}
				repondreClient();
			}catch (Exception e){
				e.printStackTrace();
			}
		}

		/**
		 * S'occupe de creer 2 sous clients
		 * @throws Exception En cas d'erreur
		 */
		private void creerClient1() throws Exception{
			ClientFibo c1 = new ClientFibo(Integer.toString(param-1),port,"localhost");
			c1.start();
			ClientFibo c2 = new ClientFibo(Integer.toString(param-2),port+1,"localhost");
			c2.start();
			while(c1.getRetour()==null || c2.getRetour() == null);
			cache.put(param,Integer.parseInt(c1.getRetour())+Integer.parseInt(c2.getRetour()));
		}

		/**
		 * S'occupe de creer 2 sous client
		 * @throws Exception En cas d'erreur
		 */
		private void creerClient2() throws Exception{
			ClientFibo c1 = new ClientFibo(Integer.toString(param-1),port-1,"localhost");
			c1.start();
			ClientFibo c2 = new ClientFibo(Integer.toString(param-2),port,"localhost");
			c2.start();
			while(c1.getRetour()==null || c2.getRetour() == null);
			cache.put(param,Integer.parseInt(c1.getRetour())+Integer.parseInt(c2.getRetour()));
		}

		private void repondreClient() throws Exception{
			sortie.println(Integer.toString(this.cache.get(param)));
			this.socket.close();

		};
	}


	/**
	 * Constructeur du serveur de calcul
	 * @param port Port sur lequel écouter
	 * @param numero Identifiant du serveyr
	 */
	public ServeurFibo(int port,int numero){
		super("ServFibo");
		this.port = port;
		this.numero = numero;
		this.cache = new HashMap<Integer,Integer>();

	}


	/**
	 * Fonction principale du serveur. S'occupe essentiellement de traiter les arrivees de client
	 */
	public void run(){
		try{
			ServerSocket serverSocket = new ServerSocket(port);
			while(true){
				Socket socket = serverSocket.accept();
				ClientThread tc = new ClientThread(socket, cache, port);
				tc.start();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public ServeurFibo getServeur2() {
		return serveur2;
	}


	public void setServeur2(ServeurFibo serveur2) {
		this.serveur2 = serveur2;
	}


	public int getNumero() {
		return numero;
	}

}
