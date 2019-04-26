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
	public void replace(FilterBypass fb, int offset, int length, String text,
	AttributeSet attrs) throws BadLocationException {
		super.replace(fb, offset, length, text.replaceAll(limit,""), attrs);
	}
}