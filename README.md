# KEGG_browser

Ce projet a été réalisé dans le cadre de ma première année de master de bioinformatique. L'objectif était de travailler sur des données biologiques issues de différentes espèces bactériennes, portant à la fois sur le métabolisme et la génomique. Ce projet a permis de se familiariser avec une base de connaissances de référence en matière et de métabolisme et l'utilisation d'une API REST ainsi qu'une bibliothèque externe. Ceci a conduit à l'élaboration d'une interface graphique utilisateur permettant de passer d'une voie métabolique au génome et inversement.<br>
Enoncé du projet : https://www.lri.fr/~zaharia/EdC2017/Projet_EdC_2017_2018.pdf

## Informations

Ce projet nécessite de créer un dossier <code>bin</code>, <code>conf</code>, <code>doc</code>, <code>image</code> et <code>texte</code> après le téléchargement des fichiers.<br>
Pour lancer le programme :
<pre>
<code>cd KEGG_browser/src
javac -cp ../lib/swingbox-1.1-bin.jar:. -d ../bin IHM.java
cd ../bin
java -cp ../lib/*:. IHM</code>
</pre>
