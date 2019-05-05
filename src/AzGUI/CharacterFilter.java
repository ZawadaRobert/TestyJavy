package AzGUI;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class CharacterFilter extends DocumentFilter {
	
	private String limit;
	
	public CharacterFilter(String limit) {
		this.limit=limit;
	}
	
	@Override
	public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
		String text, AttributeSet attrs) throws BadLocationException {
		if (offset >= fb.getDocument().getLength()) {
			text=text.replaceAll(limit,"");
		}
		super.replace(fb, offset, length, text, attrs);
	}
}

