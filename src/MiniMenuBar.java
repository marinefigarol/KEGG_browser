/**
 *
 * MiniMenuBar.java
 *
 * @author Marine Figarol & Christelle Viguier
 */


// --------------------------- Imports ---------------------------------

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel ;

import java.awt.BorderLayout;

/**
 * Creation de la barre se trouvant en haut des deux parties contenant les
 * informations. Herite de JPanel.
 */

public class MiniMenuBar extends JPanel {
	
	// ------------------------ Attributs ------------------------------
	
	Color bleu = new Color(126,192,238) ;
	
	JLabel name2 ;
	JPanel vide ;
	JButton image ;
	
	
	
	// ----------------------- Constructeur ----------------------------
	
	
	/**
	 * Permet de creer la la barre de menu telle qu'elle sera affichee
	 * au dessus des informations au lancement de l'interface, sans les
	 * specificites de chacune.
	 */
	
	public MiniMenuBar() {
		
		setPreferredSize(new Dimension(380, 20));
		
		name2 = new JLabel() ;				// Nom de la partie information
		name2.setOpaque(true) ;
		name2.setBackground(bleu) ;
		
		image = new JButton("Image") ;
		
		vide = new JPanel() ;
		
		// Composition du Layout
		setLayout(new BorderLayout()) ;
		add(name2, BorderLayout.WEST) ;
		add(image, BorderLayout.EAST) ;
		
	}
}
