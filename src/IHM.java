/**
 *
 * IHM.java
 *
 *
 * @author Marine Figarol & Christelle Viguier
 */

//import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Box;
import java.awt.Dimension;
import java.io.File ;
import javax.swing.ImageIcon ;
import java.awt.Toolkit ;

import java.awt.Container ;
import java.io.IOException ;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.util.regex.Pattern ;
import java.util.regex.Matcher ;

import java.awt.event.ActionListener ;
import java.awt.event.ActionEvent ;

import java.awt.event.MouseEvent ;
import java.awt.event.MouseListener ;


/**
 * Cree l'interface utilisateur pour le Kegg Browser.
 * 
 * USAGE :
 * 	Dossier scr : 
 * 		javac -cp ../lib/swingbox-1.1-bin.jar:. -d ../bin IHM.java
 *  Dossier bin :
 * 		java -cp ../lib/*:. IHM
 * 
 */

public class IHM extends JFrame implements ListSelectionListener, 
		MouseListener, ActionListener {
	
	// ------------------------ Attributs ------------------------------
	
	private static final int MARGE = 8 ;
	
	private static final int LARGEUR = 1600 ;
	private static final int HAUTEUR = 1000 ;
	
	private final int LARGEUR_MIN = 1000 ;
	private final int HAUTEUR_MIN = 600 ;
	
	HautIHM haut ;
	BasIHM bas ;
	
	String nouvEspece ;
	String nouvGene ;
	String nomFichier ;
	String chemin ;
	String reaction ;
	String voie ;
	String valeur ;
	JFrame fenetreReaction ;
	Recuperation recup = new Recuperation() ;
	Coordonnees co = new Coordonnees() ;
	JOptionPane pbConnexion ;
	JOptionPane pbGene ;
	JOptionPane pbPathway ;
	JOptionPane welcome ;
	
	
	
	// ----------------------- Constructeur ----------------------------
	
	/**
	 * Cree l'interface utilisateur qui sera affiche au lancement.
	 * @throws IOException leve l'exception
	 */
	
	public IHM() throws IOException {
		
		// Message d'accueil au lancement de l'application
		welcome = new JOptionPane() ;
		welcome.showMessageDialog(null, "Bienvenue sur Kegg Browser !\n\n\n"
			+ "Ce programme vous permet de rechercher des données génomiques, métaboliques ou réactionnelles dans la\n"
			+"base de données KEGG.\n\nVous pouvez :\n\n"
			+"    - Rechercher un gène dans la partie Génome Browser.Vous obtiendrez alors son contexte génomique (s'il est\n"
			+"disponible), de nombreuses informations à son sujet ainsi que les réactions dans lesquelles il est impliqué.\n"
			+"    - Rechercher une voie métabolique. Les réactions présentées en vert sont celles existantes dans l'espèce que\n"
			+"vous aurez précisée. Celles qui sont en blanc, en revanche, n'interviennent pas danscette espèce. Vous pouvez\n"
			+"cliquer sur n'importe laquelle de ces réactions afin d'obtenir ces informations et les gènes qui sont impliqués\n"
			+"dans cette réaction. Le premier gène vous sera d'ailleurs affiché dans son contexte génomique. Bien évidemment,\n"
			+"si c'est une réaction qui n'existe pas dans cette espèce, aucun gènene sera affiché, mais vous obtiendrez tout de\n"
			+"même les informations de la réaction. Il vous est également possible de cliquer sur le bouton 'image' afin\n"
			+" d'obtenir le schéma réactionnel de cette réaction.\n"
			+"    - Vous pouvez aussi cliquer sur les réactions ou les gènes présents dans les listes déroulantes, à droite.\n"
			+"Ceci aura pour effet d'afficher les informations à leur sujet.\n\n"
			+"Soyez patient ! Certaines fonctionnalités peuvent prendre du temps pour charger les précieuses informations que\n"
			+"vous désirez ! Il est nécessaire de charger de nombreuses données depuis le site KEGG et ce n'est pas instantané !\n"
			+"Il est d'ailleurs nécessaire de disposer d'une connexion internet, sauf si vous disposez de votre propre base de\n"
			+"données en local, qui se formera au fur et à mesure que vous effecturez vos recherches. ;)\n\n"
			+"Nous espérons que cette application vous sera d'une grande utilité. N'hésitez pas à nous contacter si vous\n"
			+"rencontrez un problème ou avez une amélioration à nous suggérer !\n"
			+"Amis informaticiens, avant toute question sur la face cachée de cette application (sous-entendu le code, bien\n"
			+"évidemment ;)), n'hésitez pas à consulter la Javadoc ! Elle a été réalisée avec amour, afin que vous compreniez\n"
			+"au mieux chacune de nos fonctions ! Toute contribution bénévole sera évidemment la bienvenue !\n\n\n"
			+"Kegg browser, version 1.2.9", "Bienvenue !" , JOptionPane.INFORMATION_MESSAGE, new ImageIcon("../KEGG_database_logo.gif")) ;
			
			
		// Interface du Kegg Browser
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
		setTitle("KEGG browser") ;
		setSize(LARGEUR,HAUTEUR) ;
		setMinimumSize(new Dimension(LARGEUR_MIN, HAUTEUR_MIN)) ;
		
		// Récupération du conteneur
		Container myContainer = getContentPane() ;
		
		//Partie haute de l'interface
		haut = new HautIHM() ;
		haut.setPreferredSize(new Dimension(LARGEUR/2-4, HAUTEUR/2));
		haut.menu.search.addActionListener(this) ;
		
		// Partie basse de l'interface
		bas = new BasIHM() ;
		bas.setPreferredSize(new Dimension(LARGEUR/2-4, HAUTEUR/2));
		bas.pathway.pathway.addMouseListener(this) ;
		bas.menu.search.addActionListener(this) ;
		bas.menu.infoBar.image.addActionListener(this) ;
		
		/* 
		 * Séparation de la fenetre en 2 partie égale pour la partie génome
		 * et la partie métabolisme.
		 */
		Box ihm = Box.createVerticalBox() ;
		Dimension marge = new Dimension(LARGEUR, MARGE) ;
		ihm.add(new Box.Filler(marge, marge, marge)) ;
		ihm.add(haut) ;
		ihm.add(new Box.Filler(marge, marge, marge)) ;
		ihm.add(bas) ;
		ihm.add(new Box.Filler(marge, marge, marge)) ;
		
		// Ajout au JFrame
		myContainer.add(ihm) ;
		
		// Ajout de listener sur les JList
		bas.informations.involved.addListSelectionListener(this);
		haut.informations.involved.addListSelectionListener(this);
	}
	
	// ---------------------------- Main -------------------------------
	
	public static void main (String[] args) throws IOException {
		
		IHM kegg = new IHM() ;
		kegg.setVisible(true) ;
		kegg.pack() ;
	}
	
	
	
	// ------------------------- Méthodes ------------------------------
	
	
	/**
	 * Implementation de la methode actionPerformed de la classe 
	 * ActionListener. Indique les actions a realiser si le bouton 'search'
	 * ou le bouton 'image' est active.
	 * @param e l'action realisee par l'utilisateur et recuperee par un
	 * 		listener.
	 */
	
	public void actionPerformed(ActionEvent e) {
		
		// Activation du bouton 'search' du genome browser (haut)
		if (e.getSource() == haut.menu.search) {
			
			/* 
			 * Récupération du texte entré par l'utilisateur dans les cases
			 * prévues à cet effet.
			 */
			nouvEspece = haut.menu.species.getText() ;
			nouvGene = haut.menu.ID.getText() ;
			
			// ------------- Affichage du genome map -------------------
			affichageGenomeMap(nouvEspece, nouvGene) ;
			
			// ------------ Affichage du Gene information --------------
			affichageGeneInformation(nouvEspece, nouvGene) ;
			
			// Récupération des réactions dans lesquelles le gène est impliqué
			reactionList(nouvEspece, nouvGene, chemin) ;
		}		
		
		
		// Activation du bouton 'search' du pathway browser (bas)
		if (e.getSource() == bas.menu.search) {
			
			/* 
			 * Récupération du texte entré par l'utilisateur dans les cases
			 * prévues à cet effet.
			 */
			nouvEspece = bas.menu.species.getText() ;
			voie = bas.menu.ID.getText() ;
			
			// ------------------ Affichage du pathway -----------------
			affichagePathway(nouvEspece, voie) ;
			
			//----- Récupération des .conf s'ils n'existent pas --------
			recupConf("map", voie) ; 			// .conf général
			recupConf(nouvEspece, voie) ;		// .conf espèce
		}
		
		// Activation du bouton 'image'
		if (e.getSource() == bas.menu.infoBar.image) {
			// Récupération de l'image de la réaction
			reactionImage(reaction) ;
			affichageReaction(reaction, chemin) ;
		}
	}
	
	
	
	/**
	 * Permet l'affichage du Genome Map si c'est possible, ou genere une
	 * fenetre avec un message d'erreur si c'est impossible
	 * @param nouvEspece l'espece dont on cherche le genome
	 * @param nouvGene le gene dont on veut le genome
	 */
	
	public void affichageGenomeMap(String nouvEspece, String nouvGene) {
		try {
			// MAJ du genome map
			haut.genomeBrowser.MAJmap(nouvEspece, nouvGene) ;
		}
		// Si on ne peut pas charger la page internet
		catch(IOException excep) {
			System.out.println("URL inaccessible !") ;
			System.out.println(excep) ;
			
			// Affichage d'une nouvelle fenetre avec message erreur
			pbConnexion = new JOptionPane() ;
			pbConnexion.showMessageDialog(null, "Impossible de charger "
				+ "la page du génome !\nVérifiez votre connexion.",
				"Erreur connexion", JOptionPane.ERROR_MESSAGE) ;
			
		}
	}


	
	/**
	 * Permet l'affichage des informations sur le gene si c'est possible,
	 * ou genere une fenetre avec un message d'erreur si c'est impossible.
	 * @param nouvEspece l'espece dont on cherche les informations
	 * @param nouvGene le gene dont on cherche les informations
	 */
	
	public void affichageGeneInformation(String nouvEspece, String nouvGene) {
		
		nomFichier = nouvEspece + nouvGene + ".txt" ;
		chemin = "../texte/" + nomFichier ;
		
		// Vérification de l'existence du fichier
		File f = new File(chemin) ;
		try {
		// S'il n'est pas présent en local, il faut le récupérer
			if (!f.isFile()) {
				recup.recuperationTexte("gene", nouvEspece, nouvGene, chemin) ;
			}
			// MAJ de la partie sur les informations du gène
			haut.informations.MAJinfo(chemin) ;
		}
		// Si on ne peut pas récupérer les infos sur le gène
		catch (Exception excep) {
			System.out.println("Affichage infos gènes impossible !") ;
			System.out.println(excep) ;
			// Affichage d'une nouvelle fenetre avec message erreur
			pbGene = new JOptionPane() ;
			pbGene.showMessageDialog(null, "Impossible de charger "
				+ "les\ninformations du gène !\nVérifiez qu'il existe... ;)",
				"OUPS !", JOptionPane.ERROR_MESSAGE) ;
		}
	}
	
	
	
	/**
	 * Permet l'affichage de la voie metabolique si c'est possible,
	 * ou genere une fenetre avec un message d'erreur si c'est impossible.
	 * @param nouvEspece l'espece pour laquelle on veut la voie metabolique
	 * @param voie la voie metabolique que l'on veut afficher
	 */
	
	public void affichagePathway(String nouvEspece, String voie) {
		
		nomFichier = nouvEspece + voie + ".png" ;
		chemin = "../image/" + nomFichier ;
		
		// Vérification de l'existence du fichier
		File path = new File(chemin) ;
		try {
			// S'il n'est pas présent en local, il faut le récupérer
			if (!path.isFile()) {
				recup.recuperationImage(nouvEspece, voie, chemin) ;
			}
			
			// MAJ du pathway browser
			bas.pathway.MAJpathway(chemin) ;
			// Reset des coordonnées du rectangle de sélection
			bas.pathway.x_rect = 0 ;
			bas.pathway.y_rect = 0 ;
			bas.pathway.largeurRect = 0;
			bas.pathway.hauteurRect = 0 ;
			validate() ;
		}
		catch (Exception excep) {
			System.out.println("Affichage pathway impossible !") ;
			System.out.println(excep) ;
			// Affichage d'une nouvelle fenetre avec message erreur
			pbPathway = new JOptionPane() ;
			pbPathway.showMessageDialog(null, "Impossible de charger "
				+ "les informations\nde cette voie métabolique !\n" +
				"Vérifiez qu'elle existe... ;)", "OUPS !", 
				JOptionPane.ERROR_MESSAGE) ;
		}
	}
	
	
	/**
	 * Permet de verifier si le fichier .conf general existe deja ou pas,
	 * et de le recuperer si besoin. Affiche un message dans la console
	 * si la recuperation echoue.
	 * @param espece l'espece dont on veut recuperer le .conf ("map" si general)
	 * @param voie la voie metabolique dont on veut recuperer le .conf
	 */
	
	public void recupConf(String espece, String voie) {
		
		try {
			// .conf espèce
			nomFichier = espece + voie + ".conf" ;
			chemin = "../conf/" + nomFichier ;
			File f = new File(chemin) ;
			
			//S'il n'est pas présent en local, il faut le récupérer
			if (!f.isFile()) {
				recup.recuperationTexte("conf", espece, voie, chemin) ;
			}
			/*
			 *  Si c'est un point .conf général, on récupère les coordonnées
			 * des réactions.
			 */
			if (espece.equals("map")) co.updateCoord(chemin) ;
			
		}	
		catch (Exception excep) {
			System.out.println("Récupération .conf " + espece + " impossible !") ;
			System.out.println(excep) ;
		}
	}
	
	
	/**
	 * Permet de recuperer l'image de la reaction.
	 * @param reaction la reaction a recuperer
	 */
	
	public void reactionImage(String reaction) {
		
		try {
				
			nomFichier = reaction + ".gif" ;
			chemin = "../image/" + nomFichier ;
			File fileReac = new File(chemin) ;
			
			//S'il n'est pas présent en local, il faut le récupérer
			if (!fileReac.isFile()) {
				recup.recuperationImage("", reaction, chemin) ;
			}
			
		}	
		catch (Exception excep) {
			System.out.println("Récupération image réaction impossible !") ;
			System.out.println(excep) ;
		}
	}
	
	
	/**
	 * Permet de mettre a jour la JList contenant les reactions dans
	 * lesquelles le genes est implique.
	 * @param nouvEspece l'espece du gene
	 * @param nouvGene le gene dont on veut connaitre les reactions
	 * @param chemin le chemin d'accession au fichier
	 */
	
	public void reactionList(String nouvEspece, String nouvGene, String chemin) {
		
		// Récupération des réactions dans lesquelles le gène est impliqué
		try {
			co.updateReaction(nouvEspece, nouvGene, chemin) ;
		}
		catch (Exception excep) {
			System.out.println("impossible de récupérer les réactions !") ;
			System.out.println(excep) ;
		}
		
		// On vide la ModelList
		haut.informations.model.clear() ;
		
		// On remplit la ModelList
		for (int j = 0 ; j < co.tabReaction.size() ; j++) {
			haut.informations.model.addElement(co.tabReaction.get(j)) ;
		}
		// On remplit la JList des involved in reaction à partir du model
		haut.informations.involved.setModel(haut.informations.model) ;
	}
	
	
	
	/**
	 * Permet l'affichage de l'image de la reaction.
	 * @param reaction la reaction a afficher
	 * @param chemin le chemin d'accession a l'image
	 */
	
	public void affichageReaction(String reaction, String chemin) {
		
		//Récupération de l'image
		ImageIcon icone = new ImageIcon(chemin);
		JLabel image = new JLabel(icone);
		
		// Affichage d'une nouvelle fenetre avec l'image de la réaction
		fenetreReaction = new JFrame() ;
		fenetreReaction.setTitle("Réaction " + reaction) ;
		// La fenetre aura la taille de l'image
		fenetreReaction.setSize(icone.getIconWidth()+10, icone.getIconHeight()+10) ;
		fenetreReaction.add(image);
		//repaint() ;
		// Pour centrer la fenetre
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize() ;
		fenetreReaction.setLocation(dim.width/2 - fenetreReaction.getWidth()/2,
				dim.height/2 - fenetreReaction.getHeight()/2) ;
		fenetreReaction.setVisible(true) ;
	}
	
	
	/**
	 * Permet l'affichage des informations sur la reaction.
	 * @param nouvEspece l'espece dont provient la reaction
	 * @param reaction la reaction dont on veut les informations
	 * @param index le numero de la reaction dans la JList
	 */
	
	public void affichageReactionInformation(String nouvEspece, String reaction,
			int index) {
		
		nomFichier = reaction + ".txt" ;
		chemin = "../texte/" + nomFichier ;
		
		// Vérification de l'existence du fichier
		File f = new File(chemin) ;
		try {
			// S'il n'est pas présent en local, il faut le récupérer
			if (!f.isFile()) {
				recup.recuperationTexte("reaction", "", reaction, chemin) ;
			}
			
			// MAJ de la partie sur les informations du gène
			bas.informations.MAJinfo(chemin) ;
			
			// MAJ de la liste des gènes
			int a = co.tabCoord2.get(index*4) ;
			int b = co.tabCoord2.get(index*4+1) ;
			int c = co.tabCoord2.get(index*4+2) ;
			int d = co.tabCoord2.get(index*4+3) ;
			
			// MAJ des coorodonnées du rectangle de sélection
			bas.pathway.x_rect = a + bas.pathway.pathway.getX() ;
			bas.pathway.y_rect = b + bas.pathway.pathway.getY() ;
			bas.pathway.largeurRect = c-a ;
			bas.pathway.hauteurRect = d-b ;
			repaint() ;
			
			// Récupération de la liste des gènes impliqués
			chemin = "../conf/" + nouvEspece + voie + ".conf" ;
			geneList(chemin, a, b, c, d, false) ;
			
			// Récupération de l'image de la réaction
			chemin = "../image/" + reaction + ".gif" ;
			reactionImage(reaction) ;
			
			// Récupération du .conf générique + MAJ coordonnées réaction
			recupConf("map", voie) ;
		}
		
		catch (Exception excep) {
			System.out.println("Affichage infos réaction impossible !") ;
			System.out.println(excep) ;
		}
	}
	
	
	/**
	 * Permet l'affichage des informations sur la reaction. Version 2,
	 * quand on ne passe pas par la JList
	 * @param nouvEspece l'espece dont provient la reaction
	 * @param reaction la reaction dont on veut les informations
	 * @param a la 1ere coordonnee de la reaction
	 * @param b la 2e coordonnee de la reaction
	 * @param c la 3e coordonnee de la reaction
	 * @param d la 4e coordonnee de la reaction
	 */
	
	public void affichageReactionInformation2(String nouvEspece, String reaction,
			int a, int b, int c, int d) {
		
		nomFichier = reaction + ".txt" ;
		chemin = "../texte/" + nomFichier ;
		
		// Vérification de l'existence du fichier
		File f = new File(chemin) ;
		try {
			// S'il n'est pas présent en local, il faut le récupérer
			if (!f.isFile()) {
				recup.recuperationTexte("reaction", "", reaction, chemin) ;
			}
			
			// MAJ de la partie sur les informations du gène
			bas.informations.MAJinfo(chemin) ;
			
			// Récupération de la liste des gènes impliqués
			chemin = "../conf/" + nouvEspece + voie + ".conf" ;
			geneList(chemin, a, b, c, d, true) ;
			
			// Récupération de l'image de la réaction
			chemin = "../image/" + reaction + ".gif" ;
			reactionImage(reaction) ;
			
			// Récupération du .conf générique + MAJ coordonnées réaction
			recupConf("map", voie) ;
		}
		
		catch (Exception excep) {
			System.out.println("Affichage infos réaction impossible !") ;
			System.out.println(excep) ;
		}
	}
	
	
	/**
	 * Implementation de la fonction valueChanged. Permet d'effectuer une
	 * action quand une valeur est selectionnee dans la JList, ie quand
	 * on selectionne un gene. Permet d'afficher les informations sur ce
	 * gene dans la partie du haut.
	 */
	 
	public void valueChanged(ListSelectionEvent e) {
		
		// Si on change le gène de la liste 'involves genes' (JList bas)
		if (e.getSource() == bas.informations.involved
				&& bas.informations.involved.getSelectedValue() != null) {
			
			nouvGene = bas.informations.involved.getSelectedValue() ;
			nouvEspece =  bas.menu.species.getText() ;
			
			if (nouvEspece.equals("")) {
				nouvEspece =  haut.menu.species.getText() ;
			}
			
			// ------------- Affichage du genome map -------------------
			affichageGenomeMap(nouvEspece, nouvGene) ;
			
			// ------------ Affichage du Gene information --------------
			affichageGeneInformation(nouvEspece, nouvGene) ;
			
			// Récupération des réactions dans lesquelles le gène est impliqué
			reactionList(nouvEspece, nouvGene, chemin) ;
			
		}
		
		// Si on change la réaction de la liste 'involved in reaction' (JList haut)
		if (e.getSource() == haut.informations.involved 
			&& haut.informations.involved.getSelectedValue() != null
			&& !haut.informations.involved.getValueIsAdjusting()) {
			
			int index = haut.informations.involved.getAnchorSelectionIndex() ;
			
			// Récupération de la valeur de la case sélectionnée
			valeur = haut.informations.involved.getSelectedValue() ;
			
			// Récupération de l'espèce
			nouvEspece =  haut.menu.species.getText() ;
			if (nouvEspece.equals("")) {
				nouvEspece =  bas.menu.species.getText() ;
			}
			
			// Pattern pour récupérer la réaction et la voie métabolique
			String motifReaction = "(R\\d+)" ;
			String voieMotif = nouvEspece + "(\\d+)" ;
			Pattern patternReaction = Pattern.compile(motifReaction) ;
			Pattern patternVoie = Pattern.compile(voieMotif) ;
			
			// Récupération de la réaction
			Matcher matcherReaction = patternReaction.matcher(valeur) ;
			if (matcherReaction.find()) reaction = matcherReaction.group(1) ;
			// Récupération de la voie métabolique
			Matcher matcherVoie = patternVoie.matcher(valeur) ;
			if (matcherVoie.find()) voie = matcherVoie.group(1) ;
			
			// MAJ et affichage du pathway browser
			affichagePathway(nouvEspece, voie) ;
			
			// --------- Affichage du Reaction information -------------
			affichageReactionInformation(nouvEspece, reaction, index) ;
		}
	}
	
	
	/**
	 * Implementation de la methode mousePressed, appelee lorsque l'on
	 * presse le bouton gauche de la souris. Charge les informations liees
	 * a la reation sur laquelle l'utilisateur a clique.
	 * @param e l'action realisee par la souris.
	 */
	
	public void mousePressed(MouseEvent e) {
		// Récupération des coordonnées du pathway (image)
		int xloc = bas.pathway.pathway.getHorizontalAlignment() ;
		int yloc = bas.pathway.pathway.getVerticalAlignment() ;
		
		//System.out.println(xloc + " " + yloc) ;
		
		// Définition des coordonnées relatives de la souris dans l'image
		int posx = (int) (e.getX() - xloc) ;
		int posy = (int) (e.getY() - yloc) ;
		
		int a ; int b ; int c ; int d ;
		
		// Parcours du tableau qui stocke les coordonnées des réactions
		for (int i = 0 ; i < co.tabCoord.size() ; i+=5) {
			
			a = Integer.parseInt(co.tabCoord.get(i)) ;
			b = Integer.parseInt(co.tabCoord.get(i+1)) ;
			c = Integer.parseInt(co.tabCoord.get(i+2)) ;
			d = Integer.parseInt(co.tabCoord.get(i+3)) ;
			
			// Si on est dans le rectangle de la réaction
			if (posx > a && posx < c && posy > b && posy < d) {
				
				// MAJ des coordonnées du rectangle de sélection
				bas.pathway.x_rect = a + bas.pathway.pathway.getX() ;
				bas.pathway.y_rect = b + bas.pathway.pathway.getY() ;
				bas.pathway.largeurRect = c-a ;
				bas.pathway.hauteurRect = d-b ;
				repaint() ;
				
				// -------- Affichage du Reaction information ----------
				
				// Récupération de la réaction
				reaction = co.tabCoord.get(i+4) ;
				
				// Récupération .conf
				recupConf(nouvEspece, voie) ;
				
				// MAJ et affichage des informations
				affichageReactionInformation2(nouvEspece, reaction, a,b,c,d) ;
			}
		
		}
	}
	
	
	/**
	 * Permet de mettre a jour la JList contenant les genes impliques dans
	 * la reaction.
	 * @param chemin le chemin d'accession au fichier
	 * @param a la 1ere coordonnee
	 * @param b la 2e coordonnee
	 * @param c la 3e coordonnee
	 * @param d la 4e coordonnee
	 * @param index pour savoir si on selectionne le 1er gene de la liste
	 */

	public void geneList(String chemin, int a, int b, int c, int d,
			boolean index) {
				
		// Récupération des gènes impliqués
		try {
			co.updateGene(chemin, a, b, c, d) ;
		}
		catch (Exception excep) {
			System.out.println("impossible de récupérer les gènes !") ;
			System.out.println(excep) ;
		}
		
		// On vide la ModelList
		bas.informations.model.clear() ;
		
		// On remplit la ModelList
		for (int j = 0 ; j < co.tabGene.size() ; j++) {
			bas.informations.model.addElement(co.tabGene.get(j)) ;
		}
		// On remplit la JList des involves genes à partir du model
		bas.informations.involved.setModel(bas.informations.model) ;
		if (index) bas.informations.involved.setSelectedIndex(0) ;
	}


	/*
	 *  Obligation de redéfinir ces fonctions même si on ne les implémente
	 * pas
	 */
	 
	public void mouseEntered(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	
	
	
}
