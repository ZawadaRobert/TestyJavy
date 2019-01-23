import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

public class GhostTextField extends JTextField implements DocumentListener {

	private Document document;
	private String ghostText;
	private JLabel label;
	
	public GhostTextField(String ghostText) {
		this.ghostText=ghostText;
		
		document = getDocument();
		document.addDocumentListener(this);
		
		label = new JLabel();
		label.setText(this.ghostText);
		label.setForeground(Color.RED);
		
		setLayout(new BorderLayout());
		add(label);
	}
	
	private void checkForPrompt() {
		if (document.getLength() > 0) {
			label.setVisible(false);
			return;
		}
        else label.setVisible(true);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		checkForPrompt();
		
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		checkForPrompt();
		
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		checkForPrompt();
		
	}

}
