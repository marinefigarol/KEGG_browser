/**
 *
 * MenuBar.java
 *
 * @author Marine Figarol & Christelle Viguier
 */


// --------------------------- Imports ---------------------------------

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField ;
import javax.swing.JLabel ;

import java.awt.BorderLayout;

/**
 * Creation de la barre de menu/recherche se trouvant en haut des deux 
 * browser. Herite de JPanel.
 */

public class MenuBar extends JPanel {
	
	// ------------------------ Attributs ------------------------------
	
	Color bleu = new Color(126,192,238) ;
	private static final int MARGE = 8 ;
	private static final int LARGEUR = 1600 ;
	
	JLabel name1 ;
	JLabel name2 ;
	JButton search ;
	JTextField species ;
	JLabel speciesLabel ;
	JTextField ID ;
	JLabel IDLabel ;
	JButton image ;
	JPanel droite ;
	JPanel centre ;
	JPanel centreSpecie ;
	JPanel centreID ;
	JPanel gauche ;
	JPanel gauche2 ;
	JPanel vide ;
	MiniMenuBar infoBar ;
	
	// ----------------------- Constructeur ----------------------------
	
	/**
	 * Permet de creer la la barre de menu telle qu'elle sera affichee au
	 * lancement de l'interface, sans les specificites de chacune.
	 */
	
	
	
	public MenuBar() {
		
		setPreferredSize(new Dimension(LARGEUR, 20));
		name1 = new JLabel() ;				// Nom du borwser
		name1.setOpaque(true) ;
		name1.setBackground(bleu) ;
		
		name2 = new JLabel() ;				// Nom de la partie information
		name2.setOpaque(true) ;
		name2.setBackground(bleu) ;
		
		search = new JButton("Search") ;
		species = new JTextField() ;
		species.setPreferredSize(new Dimension(60, 10));
		speciesLabel = new JLabel(" Species ") ;
		ID = new JTextField() ;
		ID.setPreferredSize(new Dimension(100, 10));
		IDLabel = new JLabel() ;
		vide = new JPanel() ;
		
		
		// Partie centrale de la barre
			
			// Partie centrale gauche
		centreSpecie = new JPanel() ;
		centreSpecie.setLayout(new BorderLayout(MARGE,MARGE)) ;
		centreSpecie.add(speciesLabel, BorderLayout.WEST) ;
		centreSpecie.add(species, BorderLayout.EAST) ;
		
			// Partie centrale droite
		centreID = new JPanel() ;
		centreID.setLayout(new BorderLayout(MARGE,MARGE)) ;
		centreID.add(IDLabel, BorderLayout.WEST) ;
		centreID.add(ID, BorderLayout.EAST) ;
		
			// On combine les deux
		centre = new JPanel() ;
		centre.setLayout(new BorderLayout(20,20)) ;
		centre.add(centreSpecie, BorderLayout.WEST) ;
		centre.add(centreID, BorderLayout.CENTER) ;
		centre.add(search, BorderLayout.EAST) ;
		
		// Partie de droite de la barre
		infoBar = new MiniMenuBar() ;
		
		// On combine maintenant le browser et centre
		gauche = new JPanel() ;
		gauche.setLayout(new BorderLayout(MARGE,MARGE)) ;
		gauche.add(name1, BorderLayout.WEST) ;
		gauche.add(centre, BorderLayout.EAST) ;
		
		// On combine gauche avec search
		gauche2 = new JPanel() ;
		gauche2.setLayout(new BorderLayout(MARGE,MARGE)) ;
		gauche2.add(gauche, BorderLayout.WEST) ;
		gauche2.add(search, BorderLayout.EAST) ;
		
		// Composition totale de la barre de menu
		setLayout(new BorderLayout()) ;
		add(gauche2, BorderLayout.WEST) ;
		add(vide, BorderLayout.CENTER) ;
		add(infoBar, BorderLayout.EAST) ;
	}
}
