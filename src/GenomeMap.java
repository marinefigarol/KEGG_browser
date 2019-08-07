/**
 *
 * GenomeMap.java
 *
 * 
 * @author Marine Figarol & Christelle Viguier
 */


import javax.swing.JPanel ;
import org.fit.cssbox.swingbox.BrowserPane ;
import java.net.URL ;
import java.io.IOException ;
import java.awt.Color;

/**
 * Permet la creation et l'affichage du Genome map. Herite de la classe
 * JPanel. Utilise le package swingbox pour charger la page internet.
 * Le Genome map sera affiche dans IHM.
 */

public class GenomeMap extends JPanel {
	
	// ------------------------ Attribut -------------------------------
	
	BrowserPane browser ;
	
	// ----------------------- Constructeur ----------------------------
	
	/**
	 * Cree un GenomeMap vide qui sera affiche lors du lancement de
	 * l'interface.
	 * @throws IOException leve l'exception
	 */
	
	public GenomeMap() throws IOException {
		
		browser = new BrowserPane() ;
		setBackground(Color.WHITE) ;
	}
	
	// -------------------------- Methode ------------------------------
	
	/**
	 * Permet de mettre à jour le genome map, ie d'afficher celui desire
	 * par l'utilisateur.
	 * @param specie l'espece dont on veut afficher le genome map.
	 * @param ID l'identifiant du gene que l'on veut visualiser dans le
	 * 		genome.
	 * @throws IOException leve l'exception
	 */
	
	public void MAJmap(String specie, String ID) throws IOException {
		
		// Détermine l'URL que l'on va afficher
		browser.setPage(new URL("http://www.kegg.jp/kegg-bin/show_genomemap?ORG="
				+ specie + "&ACCESSION=" + ID)) ;
		add(browser) ;								// Ajout au JPanel
		
	}
}
