/**
 *
 * HautIHM.java
 *
 *
 * @author Marine Figarol & Christelle Viguier
 */
 
 
 // --------------------------- Imports ---------------------------------
 
import javax.swing.JPanel ;
import java.awt.BorderLayout;
import javax.swing.JScrollPane ;
import java.io.IOException ;
import java.awt.Insets ;


/**
 *  Creation de la partie du haut de l'interface utilisateur, ie la partie
 * concernant le genome. Cette classe herite de JPanel et implemente
 * ActionListener afin de recuperer les actions faites par l'utilisateur.
 */

public class HautIHM extends JPanel {
	
	// ------------------------ Attributs ------------------------------
	
	private static final int MARGE = 8 ;
	
	MenuBar menu ;
	GenomeMap genomeBrowser ;
	Information informations ;
	
	
	// ----------------------- Constructeur ----------------------------
	
	/**
	 * Permet de creer la partie concernant le genome telle qu'elle sera
	 * affichee au lancement de l'interface.
	 * @throws IOException leve l'exception
	 */
	
	public HautIHM() throws IOException {
		
		// Création de la barre de menu / recherche
		menu = new MenuBar() ;
		// Spécificité de la barre de la partie haute
		menu.name1.setText(" Genome Browser ") ;
		menu.IDLabel.setText("Gene ID ") ;
		menu.infoBar.name2.setText(" Gene information ") ;
		// Le bouton 'image' n'existe pas dans la partie haute
		menu.infoBar.image.setVisible(false) ;
		
		// Création du Genome browser que l'on met dans un JScrollPane
		genomeBrowser = new GenomeMap();
		JScrollPane scrollGenome = new JScrollPane(genomeBrowser) ;

		
		// Informations sur les gènes
		informations = new Information() ;
		informations.titre.setText("Involved in reaction(s)") ;
		
		// Création du Layout pour avoir une bonne disposition
		setLayout(new BorderLayout(MARGE, MARGE)) ;
		getInsets() ;
		add(menu, BorderLayout.NORTH) ;
		add(scrollGenome, BorderLayout.CENTER) ;
		add(informations, BorderLayout.EAST) ;

	}
	
	
	// -------------------------- Methode ------------------------------
	
	/**
	 * Permet d'afficher des marges sur les cotes gauche et droit.
	 * @return Insets les marges
	 */
	
	public Insets getInsets() {
		return new Insets(0,MARGE,0,MARGE) ;
	}
	
}
