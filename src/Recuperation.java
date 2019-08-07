/**
 *
 * Recuperation.java
 *
 *
 * @author Marine Figarol & Christelle Viguier
 */
 
 
// --------------------------- Imports ---------------------------------

import java.io.* ;
import java.net.URL ;


/**
 * Permet de recuperer les donnees sur les voies metaboliques, les donnees
 * genomiques, les reactions, et les voies metaboliques generiques qui seront
 * utilisees dans les differentes parties de l'interface.
 */

public class Recuperation {
	
	// ------------------------ Attributs ------------------------------
	
	public static final String KEGG_URL = "http://rest.kegg.jp/get/" ;
	public static final String DELIMITER = "/" ;
	public static final String IMAGE = "image" ;
	public static final String CONF = "conf" ;
	public static final String ORG = "org" ;
	public static final String PNG = ".png" ;
	
	
	//~ public static void main (String[] args) throws Exception {
		
		//~ recuperationImage("eco", "00260", "eco00260.png") ;
		//~ recuperationImage("map", "00260", "map00260.png") ;
		//~ recuperationImage("", "R00480", "R00480.gif") ;
		//~ recuperationTexte("conf","eco","00260","eco00260.conf") ;
		//~ recuperationTexte("conf","map","00260","map00260.conf") ;
		//~ recuperationTexte("gene","eco","b0002","b0002.txt") ;
		//~ recuperationTexte("reaction","","R00480","R00480.txt") ;
	//~ }
	
	
	// -------------------------- Methodes -----------------------------
	
	/**
	 * Permet de recuperer les images, ie les voies metaboliques et les
	 * reactions a partir d'une URL.
	 * @param org l'organisme d'origine
	 * @param aRecup l'identifiant de la voie ou de la reaction à recuperer
	 * @param destination le chemin ou sera sauvegarde l'image
	 * @throws Exception leve l'exception
	 */
	
	public static void recuperationImage(String org, String aRecup, String
		destination) throws Exception {
		
		// Récupération de l'URL
		String image_url = KEGG_URL + org + aRecup + DELIMITER + IMAGE ;
		URL url = new URL(image_url) ;
		
		// Récupération de l'image à partir de l'URL
		InputStream is = url.openStream() ;
		OutputStream os = new FileOutputStream(destination) ;
		byte[] b = new byte[2048] ;
		int taille ;
		
		while ((taille = is.read(b)) != -1) {
			os.write(b, 0, taille) ;
		}
		
		is.close() ;
		os.close() ;
		
	}
	
	
	/**
	 * Recuperation des textes, ie des fichiers .conf, ainsi que les
	 * informations sur les genes et les reactions.
	 * @param type le type de fichier a recuperer (conf, gene, reaction)
	 * @param org l'organisme d'origine ("map" si on veut les informations
	 * 		globales d'une voie metaboliques)
	 * @param aRecup l'indentifiant de la voie, du gene ou de la reaction
	 * @param destination le chemin ou sera sauvegarde le fichier
	 * @throws Exception leve l'exception
	 */
	public static void recuperationTexte(String type, String org, String aRecup,
		String destination) throws Exception {
		
		// Récupération de l'URL en fonction du type de fichier
		String texte_url ;
		// Fichier .conf
		if (type == "conf") {
			texte_url = KEGG_URL + org + aRecup + DELIMITER + CONF ;
		}
		// Fichier contenant les informations sur un gène
		else if (type == "gene") {
				texte_url = KEGG_URL + org + ":" + aRecup ;
			}
			else { // Fichier contenant les informations sur une réaction
				texte_url = KEGG_URL + "rn:" + aRecup ;
			}
		
		URL url = new URL(texte_url) ;
		
		// Récupération du texte
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream())) ;
		FileWriter file = new FileWriter(destination) ;
		BufferedWriter os = new BufferedWriter(file) ;
		
		String inputLine ;
		while ((inputLine = in.readLine()) !=null) {
			os.write(inputLine) ;
			os.write("\n") ;
		}
		in.close() ;
		os.close() ;
	}
}
