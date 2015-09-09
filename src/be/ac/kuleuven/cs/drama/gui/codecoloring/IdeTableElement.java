package be.ac.kuleuven.cs.drama.gui.codecoloring;

import java.awt.Color;

public class IdeTableElement {

	private Color color;
	private boolean finalColor = false;
	
	private IdeState state;
	
	public IdeTableElement(Color color, boolean finalColor, IdeState state) {
		this.updateColorTo(color);
		this.finalColor = finalColor;
		
		this.state = state;
	}
	
	public IdeTableElement(Color color, boolean finalColor) {
		this(color, finalColor, IdeState.getDefault());
	}
	
	public void updateColorTo(Color newColor) {
		if (this.finalColor)
			throw new RuntimeException("A final color of an IDE table element was attempted to change.");
		if (newColor == null)
			throw new RuntimeException("The color of an IDE table element was attempted to be changed to null.");
		
		this.color = newColor;
	}
	
	public void finalizeColor() {
		this.finalColor = true;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public IdeState getNewState() {
		return this.state;
	}
}
