package Client;

// ChatClient.java

import java.util.*;
import java.net.*;
import java.io.*;

public class ClientFact{
	
	private static int port;
	private static InetAddress adresse;
	private static Socket socket;
	private static int valeurDepart;
	private int valeurCourante;
	private static int nombre_clients = 0;
	private Listen l;

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

    public void run() {
        if(nombre_clients == 1){ //Quand on crée un seul client ca veut dire qu'on envoie la valeur de depart
	        PrintStream output = null;
			try {
				output = new PrintStream(socket.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        System.out.println("Tapez la valeur à envoyer");
	        Scanner sc = new Scanner(System.in);
	        String msg;
	        while (true) {
	        		msg = sc.nextLine();
	        		valeurDepart = Integer.parseInt(msg);
	        		output.println(valeurDepart);
	        }//while        
    	}
        else //Si c'est au moins le 2eme client, c'est qu'on vient du server
        {
        	PrintStream output = null;
			try {
				output = new PrintStream(socket.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        while (true) {
	        		output.println(valeurCourante);
	        }//while        
        }
    }//run
    
    public ClientFact(InetAddress ad, int port) throws IOException
	{
    	adresse = ad;
    	this.port = port;
    	socket = new Socket(adresse, this.port);
    	l = new Listen(socket);
    	ClientFact.nombre_clients++;
    	System.out.println("nombre clients : "+nombre_clients);
    	l.start();
	}
    
    public ClientFact()
    {
    	ClientFact.nombre_clients++;
    	System.out.println("nombre clients : "+nombre_clients);
    	valeurCourante = valeurDepart - nombre_clients + 1; //Ex on veut 4! si on a 2 clients la valeur courante sera 3	
		this.run();
    }

    public static void main(String argv[]) {
        try {
            	ClientFact c = new ClientFact(InetAddress.getByName(argv[0]), Integer.parseInt(argv[1]));
            	c.run();
        }//try
        catch (Exception e) {}//catch
    }//main

}
