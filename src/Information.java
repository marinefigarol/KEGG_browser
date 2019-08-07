/**
 *
 * Information.java
 *
 *
 * @author Marine Figarol & Christelle Viguier
 */


// --------------------------- Imports ---------------------------------

import javax.swing.JPanel ;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JTextArea ;
import java.io.RandomAccessFile ;
import java.io.IOException ;
import javax.swing.JScrollPane ;
import java.awt.Font ;
import javax.swing.JLabel ;
import javax.swing.JList ;
import javax.swing.DefaultListModel ;
import javax.swing.ListSelectionModel ;


/**
 * Cree la partie qui contient les informations sur la droite : dans la
 * partie sur le genome, contient les infomations sur le gene recherche ;
 * dans la partie du metabolisme, contient les informations sur une reaction
 * precise definie par le clique de l'utilisateur.
 */

public class Information extends JPanel {
	
	// ------------------------ Attributs ------------------------------
	
	private static final int MARGE = 8 ;
	
	JTextArea info ;
	JList<String> involved ;
	RandomAccessFile txt ;
	String ligne ;
	String contenuFichier ;
	JScrollPane scrollInfo ;
	JScrollPane scrollInvolved ;
	JLabel titre ;
	JPanel bas ;
	DefaultListModel<String> model = new DefaultListModel<String>() ;
	
	
	// ----------------------- Constructeur ----------------------------
	
	/**
	 * Cree la partie sur les informations telle qu'elle sera affiche lors
	 * du lancement de l'interface utilisateur.
	 * @throws IOException lèee l'exception
	 */
	 
	public Information() throws IOException {
		
		// Récupération du fichier texte
		info = new JTextArea() ;
		info.setEditable(false) ;						// Pour qu'on ne puisse pas modifier le texte
		
		// On place les infomation dans un JScrollPane
		scrollInfo = new JScrollPane(info) ;
		scrollInfo.setPreferredSize(new Dimension(380, 270));
		
		// Juste pour avoir la case du dessous qu'on remplira plus tard
		involved = new JList<String>(model);
		involved.setSelectionMode(ListSelectionModel.SINGLE_SELECTION) ;
		
		// On place ça dans un JScrollPane
		scrollInvolved = new JScrollPane(involved) ;
		scrollInvolved.setPreferredSize(new Dimension(380, 50));
		
		// Nom de la case du bas
		titre = new JLabel() ;
		
		// Bas de la partie
		bas = new JPanel() ;
		bas.setLayout(new BorderLayout(MARGE,MARGE)) ;
		bas.add(titre, BorderLayout.NORTH) ;
		bas.add(scrollInvolved, BorderLayout.CENTER) ;
		
		// Création du layout pour avoir une bonne disposition
		setLayout(new BorderLayout(MARGE, MARGE)) ;
		add(scrollInfo, BorderLayout.CENTER) ;
		add(bas, BorderLayout.SOUTH) ;
	
	}
	
	
	// -------------------------- Methode ------------------------------
	
	/**
	 * Permet de mettre a jour les informations, ie d'afficher celles
	 * desirees par l'utilisateur.
	 * @param chemin le chemin d'acces au fichier en local contenant les
	 * 		informations desirees.
	 * @throws Exception leve l'exception
	 */
	
	public void MAJinfo(String chemin) throws Exception {
		
		contenuFichier = "" ;
		txt = new RandomAccessFile(chemin, "r") ;
		
		/*
		 *  Récupération des lignes du fichier tant que le fichier n'est
		 * pas terminé.
		*/
		while ((ligne = txt.readLine()) != null)
		{
			contenuFichier += (ligne + "\n" ) ;
		}
		// Ajout du contenu au JTextArea
		info.setText(contenuFichier) ;
		
		// Modification de la police d'écriture et de sa taille
		Font f = new Font("Arial", Font.PLAIN, 10) ;
		info.setFont(f) ;
		// Evite que la scrollBar se retrouve tout en bas du texte
		info.setCaretPosition(0) ;
		
		//validate() ;
		
	}
}
