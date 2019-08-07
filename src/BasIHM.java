/**
 *
 * BasIHM.java
 *
 *
 * @author Marine Figarol & Christelle Viguier
 */
 
 
 // --------------------------- Imports ---------------------------------
 
import javax.swing.JPanel ;
import java.awt.BorderLayout;
import javax.swing.JScrollPane ;
import java.awt.Color;
import java.io.IOException ;
import java.awt.Insets ;


/**
 *  Creation de la partie du bas de l'interface utilisateur, ie la partie
 * concernant le metabolisme. Cette classe herite de JPanel et implemente
 * ActionListener afin de recuperer les actions faites par l'utilisateur.
 */

public class BasIHM extends JPanel {
	
	// ------------------------ Attributs ------------------------------
	
	private static final int MARGE = 8 ;
	Color bleu = new Color(126,192,238) ;
	
	MenuBar menu ;
	Pathway pathway ;
	Information informations ;
	
	
	// ----------------------- Constructeur ----------------------------
	
	/**
	 * Permet de creer la partie concernant le metabolisme telle qu'elle
	 * sera affichee au lancement de l'interface.
	 * @throws IOException leve l'exception
	 */
	
	public BasIHM() throws IOException {
		
		// Barre de menu / recherche
		menu = new MenuBar() ;
		// Spécificité de la barre de la partie haute
		menu.name1.setText(" Pathway Browser ") ;
		menu.infoBar.name2.setText(" Reaction information ") ;
		menu.IDLabel.setText("Map ID ") ;
		
		
		// Création du pathway browser que l'on place dans un JScrollPane
		pathway = new Pathway() ;
		JScrollPane scrollPathway = new JScrollPane(pathway) ;
		
		// Informations sur les gènes
		informations = new Information() ;
		informations.titre.setText("Involves gene(s)") ;
		
		// Création du Layout pour avoir une bonne disposition
		setLayout(new BorderLayout(MARGE, MARGE)) ;
		getInsets() ;
		add(menu, BorderLayout.NORTH) ;
		add(scrollPathway, BorderLayout.CENTER) ;
		add(informations, BorderLayout.EAST) ;

	}


	/**
	 * Permet d'afficher des marges sur les cotes gauche et droit.
	 * @return Insets les marges
	 */
	 
	public Insets getInsets() {
		return new Insets(0,MARGE,0,MARGE) ;
	}
	
}
