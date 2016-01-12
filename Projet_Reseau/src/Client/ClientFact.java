package Client;

// ChatClient.java

import java.util.*;
import java.net.*;
import java.io.*;

class ClientFact {
	
	private int port;
	private InetAddress adresse;
	private Socket socket;
	private int valeur;
	private static int nombre_clients = 0;

    class Listen extends Thread {

        Socket socket;
        InputStream sInput;
    
        Listen(Socket socket) {
	    try {
	        this.socket = socket;
	        sInput  = socket.getInputStream();
	    }//try
	    catch (Exception e) {}
        }//Listen

        public void run() {
            Scanner sc = new Scanner(sInput);
            while (true) {
                if (sc.hasNext()) {
                    String msg = sc.nextLine();
                    System.out.println("Listener dit : "+msg);
                }//if
            }//while
        }//run

    }//Listen

    void run() throws SocketException, IOException, UnknownHostException {
        Listen l = new Listen(socket);
        l.start();
        if(nombre_clients == 1){
	        PrintStream output = new PrintStream(socket.getOutputStream());
	        Scanner sc = new Scanner(System.in);
	        String msg;
	        while (true) {
	        		msg = sc.nextLine();
	        		valeur = Integer.parseInt(msg);
	        		output.println("J'envoie au serveur la valeur : "+valeur);
	        }//while        
    	}
    }//run
    
    ClientFact(InetAddress ad, int port) throws IOException{
    	adresse = ad;
    	this.port = port;
    	socket = new Socket(adresse, this.port);
    	nombre_clients++;
    }

    public static void main(String argv[]) {
        try {
            	ClientFact c = new ClientFact(InetAddress.getByName(argv[0]), Integer.parseInt(argv[1]));
            	c.run();
        }//try
        catch (Exception e) {}//catch
    }//main

}//ChatClient
