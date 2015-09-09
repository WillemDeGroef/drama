package be.ac.kuleuven.cs.drama.gui.codecoloring;

import java.awt.Color;

public class IdeColorSet {

	public final Color normalCol;
	public final Color commentCol;
	public final Color commandCol;
	public final Color preprocessorCol;
	public final Color registerCol;
	public final Color numberCol;
	public final Color keywordCol;
	public final Color stringCol;
	public final Color comparisonCol;
	public final Color backCol;
	public final Color selfDefinedCol;

	public final static byte ID_BLACK = 0;
	public final static byte ID_DEFAULT = 1;
	public final static byte ID_DARK = 2;
	
	public final byte id;

	public IdeColorSet(byte id) {
		this.id = id;
		switch (id) {
		case 0:
			normalCol = Color.BLACK;
			commentCol = Color.BLACK;
			commandCol = Color.BLACK;
			preprocessorCol = Color.BLACK;
			registerCol = Color.BLACK;
			numberCol = Color.BLACK;
			keywordCol = Color.BLACK;
			stringCol = Color.BLACK;
			comparisonCol = Color.BLACK;
			backCol = Color.WHITE;
			selfDefinedCol = Color.BLACK;
			break;
		case 1:
			normalCol = Color.BLACK;
			commentCol = Color.GREEN.darker();
			commandCol = Color.BLUE;
			preprocessorCol = Color.ORANGE.darker();
			registerCol = Color.GRAY;
			numberCol = new Color(127, 63, 31);
			keywordCol = Color.MAGENTA;
			stringCol = numberCol.darker();
			comparisonCol = Color.BLUE;
			backCol = Color.WHITE;
			selfDefinedCol = Color.CYAN.darker();
			break;
		case 2:
			normalCol = Color.WHITE;
			commentCol = Color.GREEN.darker();
			commandCol = Color.CYAN;
			preprocessorCol = Color.ORANGE.darker();
			registerCol = Color.PINK;
			numberCol = new Color(192, 96, 0);
			keywordCol = Color.MAGENTA;
			stringCol = numberCol.darker();
			comparisonCol = new Color(32, 255, 32);
			backCol = Color.DARK_GRAY;
			selfDefinedCol = new Color(127, 127, 32);
			break;
		default:
			throw new RuntimeException("Unexisting IDE color set requested.");
		}
	}
}
