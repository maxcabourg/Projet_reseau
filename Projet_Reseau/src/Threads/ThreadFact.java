package Threads;

/*
 * Thread permettant de calculer la factorielle d'un nombre
 */
public class ThreadFact extends Thread{

	/*
	 * Valeur dont on souhaite connaitre la factorielle
	 */
	private int entree;
	
	/*
	 * Valeur resultat de la factorielle
	 */
	private int resultat;
	
	public ThreadFact(int _entree){
		super();
		entree = _entree;
	}
	
	public void run(){
		
	}
	
	public int getResultat(){
		return resultat;
	}
}
