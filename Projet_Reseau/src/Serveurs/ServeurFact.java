package Serveurs;

// Chat.java

import java.io.*;
import java.net.*;
import java.util.*;

public class ServeurFact {

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
            Scanner sc = new Scanner(sInput);
            while (true)
                if (sc.hasNext()) {
                    String msg = sc.nextLine();
                    System.out.println(name + ": " + msg);
                }//if
        }//run

    }//ClientThread

    // Server part
    
    //DONNEES MEMBRES
    ArrayList<ClientThread> socks = new ArrayList<ClientThread>();
    int port;
    int num = 0;

    ServeurFact(int port) {
	this.port = port;
    }//Chat
    
    void run() {
	try {
		    ServerSocket sServer = new ServerSocket(port); //Ecoute le port en question
		    while (true) {
				Socket s = sServer.accept(); //Accepte la connexion
				ClientThread c = new ClientThread(s, "Thread " + num);
				Scanner sc = new Scanner(s.getInputStream()); //Recupere le nombre envoye par le client
				String msg = sc.nextLine();
				System.out.println("J'ai recu: " + msg);
				socks.add(c);
				c.start();
		}//while
		
	}//try
	catch (Exception e) {}
    }//run

    public static void main(String [] argv) {
    	ServeurFact c = new ServeurFact(Integer.parseInt(argv[0]));
    	c.run();
    }//main

}//ChatServer
	    
