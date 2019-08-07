/**
 *
 * Coordonnees.java
 *
 * 
 * @author Marine Figarol & Christelle Viguier
 */

import java.util.regex.Pattern ;
import java.util.regex.Matcher ;
import java.io.RandomAccessFile ;
import java.util.ArrayList ;
import java.lang.Integer ;
import java.io.File ;


/**
 * Permet de recuperer les coordonnees des reactions et leur nom et les
 * stocke dans une liste.
 */


public class Coordonnees {
	
	RandomAccessFile txt ;
	String ligne ;
	String ligne2 ;
	String rect = "^rect" ;
	String coord = "\\((\\d+),(\\d+)\\)\\s\\((\\d+),(\\d+)\\)" ;
	String reaction = "(R\\d+)" ;
	String gene = "\\s(\\w+)\\s" ;
	Pattern patternRect = Pattern.compile(rect) ;
	Pattern patternCoord = Pattern.compile(coord) ;
	Pattern patternReaction = Pattern.compile(reaction) ;
	Pattern patternGene = Pattern.compile(gene) ;
	ArrayList<String> tabCoord = new ArrayList<String>();
	ArrayList<String> tabGene = new ArrayList<String>() ;
	ArrayList<String> tabReaction = new ArrayList<String>() ;
	ArrayList<String> tabVoie = new ArrayList<String>() ;
	ArrayList<Integer> tabCoord2 = new ArrayList<Integer>() ;
	Recuperation recup = new Recuperation() ;


	/**
	* Permet de recuperer les coordonnees des reactions et leur nom et les
	* stocke dans une liste.
	* @param chemin le chemin d'acces au fichier .conf
	* @throws Exception leve l'exception
	*/
	
	public void updateCoord(String chemin) throws Exception {
		
		tabCoord.clear() ;
		txt = new RandomAccessFile(chemin, "r") ;
		
		/*
		 *  Récupération des lignes du fichier tant que le fichier n'est
		 * pas terminé.
		*/
		while ((ligne = txt.readLine()) != null)
		{
			// Recherche des ligne commençant par 'rect'
			Matcher matcherRect = patternRect.matcher(ligne) ;
			if (matcherRect.find()) {
				
				
				// Récupération du R number s'il y en a un, sinon on s'en fiche
				Matcher matcherReaction = patternReaction.matcher(ligne) ;
				if (matcherReaction.find()) {
					
					// Récupération des coordonnées
					Matcher matcherCoord = patternCoord.matcher(ligne) ;
					if (matcherCoord.find()) {
					
						// Ajout des coordonnées au tableau
						tabCoord.add(matcherCoord.group(1)) ;
						tabCoord.add(matcherCoord.group(2)) ;
						tabCoord.add(matcherCoord.group(3)) ;
						tabCoord.add(matcherCoord.group(4)) ;
						tabCoord.add(matcherReaction.group(1)) ;
					}
					
				}
			}
		}
	}
	
	/**
	 * Permet de recuperer les genes impliques dans une reaction.
	 * @param chemin le chemin d'acces au fichier
	 * @param coord1 la 1ere coordonnee du rectangle de la reaction
	 * @param coord2 la 2e coordonnee du rectangle de la reaction
	 * @param coord3 la 3e coordonnee du rectangle de la reaction
	 * @param coord4 la 4e coordonnee du rectangle de la reaction
	 * @throws Exception leve l'exception
	 */
	
	public void updateGene(String chemin, int coord1, int coord2,
			int coord3, int coord4) throws Exception {
		
		tabGene.clear() ;	
		txt = new RandomAccessFile(chemin, "r") ;
		
		/*
		 *  Récupération des lignes du fichier tant que le fichier n'est
		 * pas terminé.
		 */
		while ((ligne = txt.readLine()) != null)
		{
			// Recherche des ligne commençant par 'rect'
			Matcher matcherRect = patternRect.matcher(ligne) ;
			if (matcherRect.find()) {
				
				// Récupération des coordonnées
				Matcher matcherCoord = patternCoord.matcher(ligne) ;
				if (matcherCoord.find()) {
					if (Integer.parseInt(matcherCoord.group(1)) == coord1 
						&& Integer.parseInt(matcherCoord.group(2)) == coord2
						&& Integer.parseInt(matcherCoord.group(3)) == coord3
						&& Integer.parseInt(matcherCoord.group(4)) == coord4) {
						
						// Recherche des gènes
						Matcher matcherGene = patternGene.matcher(ligne) ;
						while (matcherGene.find()) {
					
							// Ajout du gène au tableau
							tabGene.add(matcherGene.group(1)) ;
						}
					}
				}
			}
		}
	}
	
	
	
	/**
	 * Permet de recuperer les reactions dans lequel le gene est implique.
	 * @param specie l'espece recherchee
	 * @param rechGene le gene recherche
	 * @param chemin le chemin d'acces au fichier
	 * @throws Exception leve l'exception
	 */
	
	public void updateReaction(String specie, String rechGene, String chemin)
			throws Exception {
		
		tabVoie.clear() ;
		tabReaction.clear() ;
		tabCoord2.clear() ;	
		txt = new RandomAccessFile(chemin, "r") ;
		
		String motifEspece = specie + "(\\d+)" ;
		Pattern patternEspece = Pattern.compile(motifEspece) ;
		
		// Parcours du .txt du gène
		while ((ligne = txt.readLine()) != null) {
			// Recherche des voies métaboliques où intervient le gène
			Matcher matcherEspece = patternEspece.matcher(ligne) ;
			if (matcherEspece.find()) {
				// Ajout de la voie métabolique au tableau
				tabVoie.add(matcherEspece.group(1)) ;
			}
		} // Fin parcours du .txt de la réaction
		
		// On parcours les pathway où se trouve le gène
		for (int i = 0 ; i < tabVoie.size() ; i++) {
			// On récupère le .conf de la voie métabolique de l'espèce donné
			String chem = "../conf/" + specie + tabVoie.get(i) + ".conf" ;
			// Vérification de l'existence du fichier
			File f = new File(chem) ;
			// S'il n'est pas présent en local, il faut le récupérer
			try {
				if (!f.isFile()) {
					recup.recuperationTexte("conf", specie, tabVoie.get(i), chem) ;
				}
				
				RandomAccessFile conf = new RandomAccessFile(chem, "r") ;
				
				// Parcours du .conf de l'espèce
				while ((ligne = conf.readLine()) != null) {
					// Recherche des ligne commençant par 'rect'
					Matcher matcherRect = patternRect.matcher(ligne) ;
					if (matcherRect.find()) {
						
						// On regarder si le gène est bien celui qu'on recherche
						Pattern patternGene2 = Pattern.compile(rechGene) ;
						Matcher matcherGene2 = patternGene2.matcher(ligne) ;

						if (matcherGene2.find()) {
							// Récupération des coordonnées
							Matcher matcherCoord = patternCoord.matcher(ligne) ;
							if (matcherCoord.find()) {
								
								// Ajout des coordonnées au tableau
								int coord1 = Integer.parseInt(matcherCoord.group(1)) ;
								int coord2 = Integer.parseInt(matcherCoord.group(2)) ;
								int coord3 = Integer.parseInt(matcherCoord.group(3)) ;
								int coord4 = Integer.parseInt(matcherCoord.group(4)) ;
								
								// On récupère le .conf général de la voie métabolique
								chem = "../conf/" + "map" + tabVoie.get(i) + ".conf" ;
								
								File f2 = new File(chem) ;
								// S'il n'est pas présent en local, il faut le récupérer
								if (!f2.isFile()) {
									recup.recuperationTexte("conf", "map", tabVoie.get(i), chem) ;
								}
				
								RandomAccessFile map = new RandomAccessFile(chem, "r") ;
								
								/*
								 *  Récupération des lignes du fichier tant que le fichier n'est
								 * pas terminé.
								 */
								while ((ligne2 = map.readLine()) != null)
								{
									// Recherche des ligne commençant par 'rect'
									Matcher matcherRect2 = patternRect.matcher(ligne2) ;
									if (matcherRect2.find()) {
										// Récupération du R number s'il y en a un, sinon on s'en fiche
										Matcher matcherReaction = patternReaction.matcher(ligne2) ;
										while (matcherReaction.find()) {
											// Vérification des coordonnées
											Matcher matcherCoord2 = patternCoord.matcher(ligne2) ;
											if (matcherCoord2.find()) {
												
												if (Integer.parseInt(matcherCoord2.group(1)) == coord1 
													&& Integer.parseInt(matcherCoord2.group(2)) == coord2
													&& Integer.parseInt(matcherCoord2.group(3)) == coord3
													&& Integer.parseInt(matcherCoord2.group(4)) == coord4) {
													
													// On récupère la réaction
													String rea = matcherReaction.group(1)
														+ " @ " + specie + tabVoie.get(i) ;
													
													if (!tabReaction.contains(rea)) {
														tabReaction.add(rea) ;
														tabCoord2.add(coord1) ;
														tabCoord2.add(coord2) ;
														tabCoord2.add(coord3) ;
														tabCoord2.add(coord4) ;
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			catch (Exception e) {
				System.out.println(e) ;
			}
		}
	}
}
