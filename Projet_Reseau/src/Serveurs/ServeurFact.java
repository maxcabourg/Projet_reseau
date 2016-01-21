package Serveurs;

// Chat.java

import java.io.*;
import java.net.*;
import java.util.*;

import Client.ClientFact;

public class ServeurFact extends Thread{

    // Client part

    class ClientThread extends Thread {
    
        Socket socket;
        InputStream sInput;
        OutputStream sOutput;
        String name;
    
        ClientThread(Socket socket, String name) {
	    try {
	        this.socket = socket;
	        sOutput = socket.getOutputStream();
	        sInput  = socket.getInputStream();
	        this.name = name;
	    }//try
	    catch (Exception e) {}
        }//ClientThread

        public void run() {
            Scanner sc = new Scanner(sInput); //On recupere le flux d'entree
            while (true)
                if (sc.hasNext()) {
                    String msg = sc.nextLine();
                    System.out.println(name + ": " + msg);
                }//if
        }//run

    }//ClientThread

    // Server part
    
    //DONNEES MEMBRES
    private ArrayList<ClientThread> socks = new ArrayList<ClientThread>();
    private Map<Integer, Integer> cache = new HashMap<>();
    private int port;
    private int nbRequetes = 0;
    private int valeurDepart;
    private int produit = 1;

    ServeurFact(int port) {
	this.port = port;
    }//Chat
    
    @Override
    public void run() {
	try {
		    ServerSocket sServer = new ServerSocket(port); //Ecoute le port en question
		    while (true) {
				Socket s = sServer.accept(); //Accepte la connexion
				nbRequetes++;
				Scanner sc = new Scanner(s.getInputStream()); //Recupere le nombre envoye par le client
				String msg = sc.nextLine();
				int nombre = Integer.parseInt(msg);
				if(nbRequetes == 1)
					valeurDepart = nombre;
				if(cache.containsKey(valeurDepart)) //si on a deja sotcke la valeur
				{
					PrintStream output = new PrintStream(socks.get(0).sOutput);
					output.println("Resultat : "+cache.get(valeurDepart));
				}
				if(nombre>1)
				{
					ClientThread c = new ClientThread(s, "Client");
					socks.add(c);
					System.out.println("J'ai recu: " + msg);
					produit *= nombre;
					System.out.println("Produit = "+produit);
					new ClientFact();
				}
				else
				{
					cache.put(valeurDepart, produit);
					PrintStream output = new PrintStream(socks.get(0).sOutput);
					output.println("Resultat : "+produit);
				}
				s.close();
		}//while
		
	}//try
	catch (Exception e) {}
    }//run

    public static void main(String [] argv) {
    	ServeurFact c = new ServeurFact(Integer.parseInt(argv[0]));
    	c.run();
    }//main

}//ChatServer
	    
