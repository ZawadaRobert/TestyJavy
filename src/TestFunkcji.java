import java.io.File;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import AzGUI.AzButton;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class TestFunkcji {
	private static JFrame mainFrame;
	private static AzButton exitButton, getPathsButton, resetButton, testButton;
	
	private static void createFrame() throws TesseractException {
		
		String inputPath = "file.png";
		
		Tesseract tes = new Tesseract();
		
		String outputText = tes.doOCR(new File(inputPath));
		
		System.out.println(outputText);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					createFrame();
					
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}