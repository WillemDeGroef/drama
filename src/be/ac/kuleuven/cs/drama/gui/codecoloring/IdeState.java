package be.ac.kuleuven.cs.drama.gui.codecoloring;

public class IdeState {

	public final static byte STATE_UNCHANGED = 0;
	public final static byte STATE_DEFAULT = 1;
	public final static byte STATE_MACRO_HEADER = 2;
	
	private final boolean finalState;
	
	private byte state;
	private boolean singleWord;
	
	private IdeState(byte state, boolean singleWord, boolean finalState) {
		if (state < 0 || state >= 3)
			throw new RuntimeException("The state was attempted to be set to an invalid number.");
		this.state = state;
		this.singleWord = singleWord;
		
		this.finalState = finalState;
	}
	
	public IdeState(byte state, boolean singleWord) {
		this(state, singleWord, false);
	}
	
	public IdeState(byte state) {
		this(state, false);
	}
	
	public byte getState() {
		return this.state;
	}
	
	public boolean isSingleWord() {
		return this.singleWord;
	}
	
	public IdeState copy() {
		return new IdeState(this.state, this.singleWord);
	}
	
	public void apply(IdeState other) {
		if (finalState)
			throw new RuntimeException("An attempt was made to modify a static IDE state.");
		if (other.state != 0)
			this.state = other.state;
		this.singleWord = other.singleWord;
	}

	private final static IdeState objUnchanged = new IdeState(STATE_UNCHANGED, false, true);
	private final static IdeState objDefault = new IdeState(STATE_DEFAULT, false, true);
	
	public static IdeState getUnchanged() {
		return objUnchanged;
	}
	
	public static IdeState getDefault() {
		return objDefault;
	}
}
