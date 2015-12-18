package Threads;

/*
 * Thread permettant de calculer la factorielle d'un nombre
 * @author Max Cabourg
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
	
	/*
	 * Constructeur de ThreadFact
	 * @param _entree : Valeur dont on souhaite connaitre la factorielle
	 */
	public ThreadFact(int _entree){
		super();
		entree = _entree;
	}
	
	/*
	 * Redefinition de la methode run pour qu'elle calcule la factorielle
	 */
	@Override
	public void run(){
		
	}
	
	/*
	 * getter du resultat
	 * @return Le resultat de la factorielle
	 */
	public int getResultat(){
		return resultat;
	}
}
