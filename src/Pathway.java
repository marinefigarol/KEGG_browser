/**
 *
 * Pathway.java
 *
 * 
 * @author Marine Figarol & Christelle Viguier
 */


import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics ;
import java.awt.Graphics2D ;
import java.awt.BasicStroke ;


/**
 * Permet la creation et l'affichage du pathway browser. Herite de JPanel.
 * Le pathway browser sera affiche dans IHM.
 */


public class Pathway extends JPanel{
	
	// ------------------------ Attributs ------------------------------
	
	ImageIcon icon;
	JLabel pathway;
	
	
	// ----------------------- Constructeur ----------------------------
	
	/**
	 * Cree le pathway browser tel qu'il sera affiche au lancement de
	 * l'interface.
	 */
	
	public Pathway() {
		
		pathway = new JLabel();
		setBackground(Color.WHITE);
	}
	
	
	// -------------------------- Methode ------------------------------
	
	/**
	 * Permet de mettre a jour le pathway browser, ie d'afficher la voie
	 * metabolique desiree par l'utilisateur.
	 * @param chemin le chemin d'acces au fichier en local contenant la
	 * 		voie metabolique desiree.
	 */
	
	public void MAJpathway(String chemin) {
		icon = new ImageIcon(chemin);
		pathway.setIcon(icon);
		//JScrollPane scroll = new JScrollPane(pathway) ;
		add(pathway);
		validate() ;
	}
	
    // Initialisation des coordonnées du rectangle de sélection
    int x_rect = 0 ;
	int y_rect = 0 ;
	int largeurRect = 0 ;
	int hauteurRect = 0 ;
    
    
    /**
     * Permet la récupération du contexte graphique et l'affichage de ses
     * composants. Permet notamment de dessiner le rectangle autour d'une
     * reaction lorsqu'elle est selectionnee.
     * @param g le contexte graphique
     */
    
    public void paint(Graphics g) {
		super.paint(g) ;
		Graphics2D g2d = (Graphics2D) g ;
		g2d.setStroke(new BasicStroke(3.0f)) ;				// épaisseur du trait
		g2d.setColor(Color.RED) ;
		// Dessin du rectangle
		g2d.drawRect(x_rect, y_rect, largeurRect, hauteurRect) ;
	}

}


