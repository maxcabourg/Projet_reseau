package Serveurs;

import java.io.PrintStream;
import java.net.*;
import java.util.*;

import Client.ClientFact;

public class ServeurFact extends Thread{

	public static void main(String[] args) {
		ServeurFact server = new ServeurFact(Integer.parseInt(args[0]));
		server.start();
	}

	private int port;
	private HashMap<Integer,Integer> cache;

	private class ClientThread extends Thread{

		private int port;
		private Socket socket;
		private int param;
		private PrintStream sortie;
		private HashMap<Integer,Integer> cache;


		public ClientThread(Socket socket,HashMap<Integer,Integer> cache,int port) throws Exception{
			super();
			this.socket = socket;
			this.port=port;
			Scanner entree = new Scanner(socket.getInputStream());
			this.param = Integer.parseInt(entree.next());
			this.sortie = new PrintStream(socket.getOutputStream());
			this.cache=cache;

		}

		synchronized public void run(){
			try{	
				//Traitement de la demande
				if(this.cache.containsKey(param)){//On a deja effectue le calcul
					System.out.println("Factorielle de "+param+" vaut : "+this.cache.get(param));
				}
				else
				{
					if(param == 0)//Condition d'arrêt de récursivité
						this.cache.put(0,1);	
					else
					{
						//On crée un nouveau Client
						ClientFact c = new ClientFact(Integer.toString(param-1),this.port,"localhost");
						c.start();
						while(c.getRetour()==null);
						//On attends que le client ait sa réponse
						this.cache.put(param,Integer.parseInt(c.getRetour())*param);//On remplit le cache			
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


	/*Server de la fonction de calcul Factorielle écoutant sur le port 50000
	 * Paramètres:
	 * 1, port sur lequel le serveur écoute
	 */

	public ServeurFact(int port){
		super();
		this.port = port;
		this.cache = new HashMap<Integer,Integer>();

	}


	@SuppressWarnings("resource")
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
