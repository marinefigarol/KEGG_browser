import javax.swing.JFrame ;
import javax.swing.JScrollPane ;
import org.fit.cssbox.swingbox.BrowserPane ;
import java.net.URL ;
import java.awt.Container ;
import java.io.IOException ;

/* Pour executer :
 * 
 * Dans le dossier src :
 * javac -cp ../lib/swingbox-1.1-bin.jar -d ../bin SwingboxTest.java
 * 
 * Dans bin :
 * java -cp ../lib/*:. SwingboxTest
 * OU
 * java -cp ../lib/swingbox-1.1-bin.jar:. SwingboxTest
 */

public class SwingboxTest extends JFrame {
	
	public SwingboxTest() throws IOException {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
		setTitle("Swingbox Test") ;
		setSize(900,400) ;
		
		BrowserPane swingbox = new BrowserPane() ;
		Container myContainer = getContentPane() ;
		myContainer.add(new JScrollPane(swingbox)) ;
		swingbox.setPage(new URL("http://www.kegg.jp/kegg-bin/show_genomemap?ORG=eco&ACCESSION=b0630")) ;
	}
	
	public static void main (String[] args) throws IOException {
		
		SwingboxTest browser = new SwingboxTest() ;
		browser.setVisible(true) ;
	}
}
