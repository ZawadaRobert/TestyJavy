package AzGUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;

// Rozszerzona kalas jTextField, z dodatkowymi metodami - implementacja do rozwa¿enia
public class AzTextField extends JTextField implements DocumentListener {

	private Document document;
	private String ghostText;
	private JLabel label;
	
	public void setGhostText(String ghostText) {
		this.ghostText=ghostText;
		
		document = getDocument();
		document.addDocumentListener(this);
		
		label = new JLabel();
		label.setText(this.ghostText);
		label.setForeground(Color.GRAY);
		
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
	
	public void setDefaultText(String text) {
		setText(text);
		addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent focusEvent) {
				if (getText().equals(text)) {
					setText("");
				}
			}
			public void focusLost(FocusEvent focusEvent) {
				if (getText().equals("")) {
					setText(text);
				}
			}
		});
	}
	
	public void setFilter(String filter) {
		((AbstractDocument) getDocument()).setDocumentFilter(new CharacterFilter(filter));
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
